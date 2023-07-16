package com.swm.studywithmentor.service.impl;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.MentorDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.SignupDto;
import com.swm.studywithmentor.model.dto.StudentDto;
import com.swm.studywithmentor.model.dto.UserDetailsDto;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.dto.UserProfileDto;
import com.swm.studywithmentor.model.dto.search.MentorSearchDto;
import com.swm.studywithmentor.model.dto.search.UserSearchDto;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.user.Mentor;
import com.swm.studywithmentor.model.entity.user.Role;
import com.swm.studywithmentor.model.entity.user.Student;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.FieldRepository;
import com.swm.studywithmentor.repository.MentorRepository;
import com.swm.studywithmentor.repository.StudentRepository;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.BaseService;
import com.swm.studywithmentor.service.EmailService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import com.swm.studywithmentor.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;
    private final FieldRepository fieldRepository;
    private final EmailService emailService;
    private final ApplicationMapper mapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, StudentRepository studentRepository, MentorRepository mentorRepository, FieldRepository fieldRepository, EmailService emailService, ApplicationMapper mapper, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.mentorRepository = mentorRepository;
        this.fieldRepository = fieldRepository;
        this.emailService = emailService;
        this.mapper = mapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Return the UserDetails that is used for authentication and authorization.
     *
     * @param email the email address to look up
     * @return the UserDetails that is used for authentication and authorization
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public PageResult<UserDetailsDto> getAllUsers(UserSearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage(),
                ObjectUtils.defaultIfNull(searchDto.getPageSize(), pageSize),
                searchDto.getDirection(),
                ObjectUtils.defaultIfNull(searchDto.getOrderBy(), "id"));
        Page<User> usersPage = userRepository.findAll(pageRequest);
        return new PageResult<>(usersPage.getTotalPages(),
                usersPage.getTotalElements(),
                usersPage.stream().map(mapper::toUserDetailsDto).toList());
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Override
    public UserProfileDto getCurrentUserProfile() {
        // User in the security context holder
        User contextUser = getCurrentUser();

        // Because the context user is not in transactional, we have to fetch it again to get student and mentor profile
        User user = userRepository.findById(contextUser.getId())
                .orElseThrow(() -> {
                    log.warn("User not found: id {}", contextUser.getId());
                    return new ConflictException(User.class, ActionConflict.READ, "Conflict when updating profile");
                });
        return mapper.toUserProfileDto(user);
    }

    @Override
    public UserDto updateProfile(UserDto userDto) {
        User user = userRepository.findById(getCurrentUser().getId())
                .orElseThrow(() -> new NotFoundException(User.class, getCurrentUser().getId()));
        // Not allow changing email
        userDto.setEmail(null);
        mapper.toEntity(userDto, user);
        user = userRepository.save(user);
        return mapper.toDto(user);
    }

    @Override
    public StudentDto updateStudentProfile(StudentDto studentDto) {
        User user = getCurrentUser();
        Student student = studentRepository.findById(user.getId()).orElse(new Student());
        // create new student because i forgot to create student in data.sql
        mapper.toEntity(studentDto, student);
        if (student.getUser() == null) {
            User attachedUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new ApplicationException("UNEXPECTED_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Something happens"));
            student.setUser(attachedUser);
        }
        student = studentRepository.save(student);
        return mapper.toDto(student);
    }

    @Override
    public MentorDto updateMentorProfile(MentorDto mentorDto) {
        User user = getCurrentUser();
        Mentor mentor = mentorRepository.findById(user.getId()).orElse(new Mentor());
        // create new mentor because i forgot to create mentor in data.sql
        if (mentorDto.getField() != null && mentor.getField() != null && !mentor.getField().getId().equals(mentorDto.getField().getId())) {
            Field field = fieldRepository.findById(mentorDto.getField().getId())
                    .orElseThrow(() -> new NotFoundException(Field.class, mentorDto.getField().getId()));
            mentor.setField(field);
        }
        mapper.toEntity(mentorDto, mentor);
        if (mentor.getUser() == null) {
            User attachedUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new ApplicationException("UNEXPECTED_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Something happens"));
            mentor.setUser(attachedUser);
        }
        mentor = mentorRepository.save(mentor);
        return mapper.toDto(mentor);
    }

    @Override
    public UserDto addUser(SignupDto signupDto) {
        userRepository.findByEmail(signupDto.getEmail())
                .ifPresent(user -> {
                    throw new ConflictException(User.class,
                            ActionConflict.CREATE,
                            "User already exists. Email: " + user.getEmail(),
                            user.getEmail());
                });
        User user = mapper.toEntity(signupDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setRole(Role.USER);
        user = userRepository.save(user);

        emailService.sendEmailVerification(user.getEmail(), user.getFirstName());
        return mapper.toDto(user);
    }

    @Override
    public void verifyActivationToken(String token) {
        // Will throw exception if token is invalid
        jwtTokenProvider.validateToken(token);

        String email = jwtTokenProvider.getEmailFromJwt(token);
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    user.setEnabled(true);
                    userRepository.save(user);
                });
    }

    @Override
    public void resendActivationToken(String email) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(user -> {
                            if (user.isEnabled()) {
                                throw new ConflictException(User.class,
                                        ActionConflict.UPDATE,
                                        "User already activated. Email: " + user.getEmail(),
                                        user.getEmail());
                            }
                            emailService.sendEmailVerification(user.getEmail(), user.getFirstName());
                        },
                        () -> {
                            throw new ConflictException(User.class,
                                    ActionConflict.READ,
                                    "User not found. Email: " + email,
                                    email);
                        });
    }

    public UserProfileDto getMentorProfile(UUID mentorId) {
        User user = userRepository.findById(mentorId)
                .orElseThrow(() -> {
                    log.warn("User not found: id {}", mentorId);
                    return new ConflictException(User.class, ActionConflict.READ, "Conflict when updating profile");
                });
        UserProfileDto profileDto = mapper.toUserProfileDto(user);
        profileDto.setStudent(null);
        return profileDto;
    }

    @Override
    public PageResult<UserProfileDto> searchMentors(MentorSearchDto searchDto) {
        Predicate searchPredicate = userRepository.prepareSearchPredicate(searchDto);
        PageRequest pageRequest;
        if (searchDto.getOrderBy() != null) {
            Sort.Direction direction = searchDto.getDirection();
            if (direction == null) {
                direction = Sort.Direction.ASC;
            }
            pageRequest = PageRequest.of(searchDto.getPage(), pageSize, Sort.by(direction, searchDto.getOrderBy()));
        } else {
            pageRequest = PageRequest.of(searchDto.getPage(), pageSize);
        }
        Page<User> users = userRepository.findAll(searchPredicate, pageRequest);
        PageResult<UserProfileDto> result = new PageResult<>(
        );
        result.setResult(users.stream()
                .map(mapper::toUserProfileDto)
                .toList());
        result.setTotalElements(users.getTotalElements());
        result.setTotalPages(users.getTotalPages());
        return result;
    }

    @Override
    public void updateNotificationToken(String token) {
        User user = getCurrentUser();
        user.setNotificationToken(token);
        userRepository.save(user);
    }

    @Override
    public void deleteNotificationToken() {
        User user = getCurrentUser();
        user.setNotificationToken(null);
        userRepository.save(user);
    }
}
