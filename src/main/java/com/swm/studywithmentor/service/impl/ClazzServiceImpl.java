package com.swm.studywithmentor.service.impl;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.dto.create.ClazzCreateDto;
import com.swm.studywithmentor.model.dto.create.LessonCreateDto;
import com.swm.studywithmentor.model.dto.search.ClazzSearchDto;
import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.ClazzStatus;
import com.swm.studywithmentor.model.entity.Lesson;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.EntityOptimisticLockingException;
import com.swm.studywithmentor.model.exception.ForbiddenException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.LessonRepository;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.BaseService;
import com.swm.studywithmentor.service.ClazzService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ClazzServiceImpl extends BaseService implements ClazzService {
    private final ClazzRepository clazzRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final ApplicationMapper mapper;

    @Autowired
    public ClazzServiceImpl(ClazzRepository clazzRepository, CourseRepository courseRepository, UserService userService, EnrollmentRepository enrollmentRepository, LessonRepository lessonRepository, ApplicationMapper mapper, UserRepository userRepository) {
        this.clazzRepository = clazzRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public ClazzDto startNewClazz(ClazzCreateDto clazzDto) {
        // validate start date, end date and enrollment end date25ddba99-e339-5888-a2c1-c91dd6764391
        log.debug("Begin start new clazz: " + clazzDto);
        if (clazzDto.getStartDate().after(clazzDto.getEndDate())) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "Start date must be before end date", clazzDto.getStartDate(), clazzDto.getEndDate());
        } else if (clazzDto.getEnrollmentEndDate().after(clazzDto.getStartDate())) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "Enrollment end date must be before start date", clazzDto.getEnrollmentEndDate(), clazzDto.getStartDate());
        } else if (clazzDto.getEnrollmentEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "Enrollment end date must be after today", clazzDto.getEnrollmentEndDate());
        }

        // validate lesson start time and end time
        for (LessonCreateDto lessonCreateDto : clazzDto.getLessonCreateDtos()) {
            if (lessonCreateDto.getStartTime().after(lessonCreateDto.getEndTime())) {
                throw new ConflictException(Lesson.class, ActionConflict.CREATE, "Lesson start time must not be after end time", lessonCreateDto);
            }
        }

        Course course = courseRepository.findById(clazzDto.getCourseId())
                .orElseThrow(() -> new NotFoundException(Course.class, clazzDto.getCourseId()));
        User user = userService.getCurrentUser();
        // TODO: ensure that clazz has enough session
        // validate course owner
        if (!ObjectUtils.nullSafeEquals(user.getId(), course.getMentor().getId())) {
            throw new ForbiddenException(Clazz.class, ActionConflict.CREATE, "User does not own this course", user.getId());
        }
        // validate course status
        if (course.getStatus() == CourseStatus.DISABLE) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "Course is disable. Cannot create class", course.getId());
        } else if (course.getStatus() == CourseStatus.DRAFTING) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "Course is in drafting. Cannot create class", course.getId());
        }

        Clazz clazz = mapper.toEntity(clazzDto);
        clazz.setStatus(ClazzStatus.OPEN);

        List<Lesson> lessons = new ArrayList<>(clazzDto.getLessonCreateDtos().size());

        for (LessonCreateDto lessonCreateDto : clazzDto.getLessonCreateDtos()) {
            // validate clazz lesson time not duplicate with other clazz of the mentor's courses
            List<Lesson> timeConflictedLessons = lessonRepository.findLessonBetweenTime(lessonCreateDto.getStartTime(), lessonCreateDto.getEndTime(), user.getId());
            if (!timeConflictedLessons.isEmpty()) {
                throw new ConflictException(Lesson.class, ActionConflict.CREATE, "There are other clazz that at the same time", lessonCreateDto.getStartTime(), lessonCreateDto.getEndTime(), timeConflictedLessons);
            }
            Lesson lesson = mapper.toEntity(lessonCreateDto);
            lesson.setClazz(clazz);
            lessons.add(lesson);
        }
        clazz.setLessons(lessons);

        // Change course status to close (close to create new session)
        if (course.getStatus() != CourseStatus.CLOSE) {
            log.info("Update course status to CLOSE");
            course.setStatus(CourseStatus.CLOSE);
            courseRepository.save(course);
        }
        clazz.setCourse(course);
        course.getClazzes().add(clazz);
        clazz = clazzRepository.save(clazz);
        return mapper.toDto(clazz);
    }

    @Override
    public ClazzDto getClazz(UUID id) {
        Clazz clazz = clazzRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Clazz.class, id));
        return mapper.toDto(clazz);
    }

    @Override
    public List<ClazzDto> getClazzesByCourse(UUID courseId) {
        // TODO: pagination and maybe searching, filtering, sorting
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(Course.class, courseId));
        return course.getClazzes().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public PageResult<ClazzDto> searchClazzes(ClazzSearchDto dto) {
        PageRequest pageRequest;
        if (dto.getOrderBy() != null) {
            Sort.Direction direction = dto.getDirection();
            if (direction == null) {
                direction = Sort.Direction.ASC;
            }
            pageRequest = PageRequest.of(dto.getPage(), pageSize, Sort.by(direction, dto.getOrderBy()));
        } else {
            pageRequest = PageRequest.of(dto.getPage(), pageSize);
        }
        Predicate predicate = clazzRepository.prepareSearchPredicate(dto);
        Page<Clazz> clazzes = clazzRepository.findAll(predicate, pageRequest);
        PageResult<ClazzDto> resultPage = new PageResult<>();
        resultPage.setResult(clazzes.stream()
                // TODO: return course information as well
                .map(mapper::toDto)
                .toList());
        resultPage.setTotalPages(clazzes.getTotalPages());
        resultPage.setTotalElements(clazzes.getTotalElements());
        return resultPage;
    }

    @Override
    public ClazzDto updateClazz(ClazzDto clazzDto) {
        if (clazzDto.getStartDate().after(clazzDto.getEndDate())) {
            throw new ConflictException(Clazz.class, ActionConflict.UPDATE, "Start date must be before end date", clazzDto.getStartDate(), clazzDto.getEndDate());
        } else if (clazzDto.getEnrollmentEndDate().after(clazzDto.getStartDate())) {
            throw new ConflictException(Clazz.class, ActionConflict.UPDATE, "Enrollment end date must be before start date", clazzDto.getEnrollmentEndDate(), clazzDto.getStartDate());
        } else if (clazzDto.getEnrollmentEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new ConflictException(Clazz.class, ActionConflict.UPDATE, "Enrollment end date must be after today", clazzDto.getEnrollmentEndDate());
        }

        Clazz clazz = findClazz(clazzDto.getId());
        checkCurrentUserPermission(clazz, ActionConflict.UPDATE);
        if (!Objects.equals(clazzDto.getVersion(), clazz.getVersion())) {
            throw new EntityOptimisticLockingException(clazz, clazz.getId());
        }
        if (clazz.getCourse().getStatus() == CourseStatus.DISABLE) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "clazz.getCourse() is disable. Cannot update class", clazz.getCourse().getId());
        }

        mapper.toEntity(clazzDto, clazz);
        clazz = clazzRepository.save(clazz);
        return mapper.toDto(clazz);
    }

    @Override
    public void deleteClazz(UUID id) {
        Clazz clazz = findClazz(id);
        checkCurrentUserPermission(clazz, ActionConflict.DELETE);
        long numOfEnrollment = enrollmentRepository.countEnrollmentByClazz(clazz);
        if (numOfEnrollment != 0) {
            throw new ConflictException(Clazz.class, ActionConflict.DELETE, "Cannot delete clazz when there are enrollments", id);
        }

        clazzRepository.delete(clazz);
    }

    private Clazz findClazz(UUID id) {
        return clazzRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Clazz.class, id));
    }

    @Override
    public ClazzDto closeEnrollment(UUID id) {
        Clazz clazz = findClazz(id);
        checkCurrentUserPermission(clazz, ActionConflict.UPDATE);
        clazz.setStatus(ClazzStatus.CLOSE);
        clazz.setEnrollmentEndDate(Date.valueOf(LocalDate.now()));
        clazz = clazzRepository.save(clazz);
        return mapper.toDto(clazz);
    }

    @Override
    public List<UserDto> getEnrolledStudent(UUID id) {
        checkCurrentUserPermission(id, ActionConflict.READ);
        return userRepository.findUserEnrollInCourse(id).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<LessonDto> getClazzFromCourse(UUID id) {
        Clazz clazz = findClazz(id);
        return lessonRepository.getLessonByClazz(clazz).stream()
                .map(mapper::toDto)
                .toList();
    }

    private void checkCurrentUserPermission(UUID id, ActionConflict actionConflict) {
        Clazz clazz = findClazz(id);
        checkCurrentUserPermission(clazz, actionConflict);
    }

    private void checkCurrentUserPermission(Clazz clazz, ActionConflict actionConflict) {
        User user = userService.getCurrentUser();
        if (clazz.getCourse().getMentor().getId() != user.getId()) {
            throw new ForbiddenException(Clazz.class, actionConflict, "User does not own the course", user.getId());
        }
    }
}
