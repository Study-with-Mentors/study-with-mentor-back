package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.dto.query.SearchRequest;
import com.swm.studywithmentor.model.dto.query.SearchSpecification;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.EnrollmentService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
//TODO: validate ID format
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final ApplicationMapper applicationMapper;
    private final Function<UUID, Enrollment> findById;
    private final UserRepository userRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, ApplicationMapper applicationMapper, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.applicationMapper = applicationMapper;
        this.userRepository = userRepository;
        findById = id -> enrollmentRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("NOT_FOUND", HttpStatus.NOT_FOUND , "Not found enrollment " + id.toString()));
    }

    @Override
    public List<EnrollmentDto> getEnrollments() {
        //TODO: use specification and pagination
        var enrollments = enrollmentRepository.findAll();
        return applicationMapper.enrollmentToDto(enrollments);
    }

    @Override
    public List<EnrollmentDto> searchEnrollments(SearchRequest searchRequest) {
        SearchSpecification<Enrollment> searchSpecification = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPage(searchRequest.getPage(), searchRequest.getSize());
        var result = enrollmentRepository.findAll(searchSpecification, pageable);
        return applicationMapper.enrollmentToDto(result.get().toList());
    }

    @Override
    public EnrollmentDto getEnrollmentById(UUID id) {
        var enrollment = findById.apply(id);
        return applicationMapper.enrollmentToDto(List.of(enrollment)).get(0);
    }

    @Override
    public EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto) {
        var enrollment = applicationMapper.enrollmentToEntity(enrollmentDto);
        //TODO: use UserService to get user
        var user = userRepository.findById(enrollmentDto.getStudent().getId())
                .orElseThrow(() -> new ApplicationException("Not Found", HttpStatus.NOT_FOUND, "Not found user: " + enrollmentDto.getStudent().getId().toString()));
        enrollment.setStudent(user);
        enrollment = enrollmentRepository.save(enrollment);
        return applicationMapper.enrollmentToDto(enrollment);
    }

    @Override
    public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto) {
        var enrollment = findById.apply(enrollmentDto.getId());
        var user = enrollmentDto.getStudent();
        // FIXME: Optimistic locking
        if(user != null && user.getId() != null && !enrollment.getStudent().getId().toString().equals(enrollmentDto.getStudent().getId().toString())) {
            //TODO: use UserService to get user
            var newUser = userRepository.findById(enrollmentDto.getStudent().getId())
                    .orElseThrow(() -> new ApplicationException("Not Found", HttpStatus.NOT_FOUND, "Not found user: " + enrollmentDto.getStudent().getId().toString()));
            enrollment.setStudent(newUser);
        }
        applicationMapper.enrollmentToEntity(enrollment, enrollmentDto);
        enrollment = enrollmentRepository.save(enrollment);

        return applicationMapper.enrollmentToDto(enrollment);
    }

    @Override
    public void deleteEnrollment(UUID id) {
        var enrollment = findById.apply(id);
        enrollmentRepository.delete(enrollment);
    }
}
