package com.swm.studywithmentor.service.impl;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.SessionDto;
import com.swm.studywithmentor.model.dto.create.CourseCreateDto;
import com.swm.studywithmentor.model.dto.search.CourseSearchDto;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.Image;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.EntityOptimisticLockingException;
import com.swm.studywithmentor.model.exception.ExceptionErrorCodeConstants;
import com.swm.studywithmentor.model.exception.ForbiddenException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.FieldRepository;
import com.swm.studywithmentor.service.BaseService;
import com.swm.studywithmentor.service.CourseService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import com.swm.studywithmentor.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class CourseServiceImpl extends BaseService implements CourseService {
    private final CourseRepository courseRepository;
    private final ClazzRepository clazzRepository;
    private final FieldRepository fieldRepository;
    private final UserService userService;
    private final ApplicationMapper mapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ClazzRepository clazzRepository, FieldRepository fieldRepository, UserService userService, ApplicationMapper mapper) {
        this.courseRepository = courseRepository;
        this.clazzRepository = clazzRepository;
        this.fieldRepository = fieldRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public CourseDto getCourseById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionErrorCodeConstants.NOT_FOUND, HttpStatus.NOT_FOUND, "Course not found. Id: " + id));

        if (!isCourseCanBeSeenByAll(course)) {
            User user = userService.getCurrentUser();
            if (!course.getMentor().getId().equals(user.getId())) {
                log.info("Unauthorized access to course. User ID {}", user.getId());
                throw new NotFoundException(Course.class, course.getId());
            }
        }
        return mapper.toDto(course);
    }

    @Override
    public PageResult<CourseDto> searchCourses(CourseSearchDto courseSearchDto, boolean visibleOnly) {
        int pageSize = ObjectUtils.defaultIfNull(courseSearchDto.getPageSize(), super.pageSize);
        Predicate searchPredicate = courseRepository.prepareSearchPredicate(courseSearchDto, visibleOnly);
        PageRequest pageRequest;
        if (courseSearchDto.getOrderBy() != null) {
            Sort.Direction direction = courseSearchDto.getDirection();
            if (direction == null) {
                direction = Sort.Direction.ASC;
            }
            pageRequest = PageRequest.of(courseSearchDto.getPage(), pageSize, Sort.by(direction, courseSearchDto.getOrderBy()));
        } else {
            pageRequest = PageRequest.of(courseSearchDto.getPage(), pageSize);
        }
        Page<Course> courses = courseRepository.findAll(searchPredicate, pageRequest);
        PageResult<CourseDto> resultPage = new PageResult<>();
        resultPage.setResult(courses.stream()
                .map(mapper::toDto)
                .toList());
        resultPage.setTotalPages(courses.getTotalPages());
        resultPage.setTotalElements(courses.getTotalElements());
        return resultPage;
    }

    @Override
    public PageResult<CourseDto> searchCourses(CourseSearchDto courseSearchDto) {
        return searchCourses(courseSearchDto, true);
    }

    @Override
    public CourseDto createCourse(CourseCreateDto courseDto) {
        Course course = mapper.toEntity(courseDto);
        course.setId(null);
        course.setStatus(CourseStatus.DRAFTING);
        Field field = fieldRepository.findById(courseDto.getFieldId())
                .orElseThrow(() -> new NotFoundException(Field.class, courseDto.getFieldId()));
        User user = userService.getCurrentUser();
        course.setMentor(user);
        course.setField(field);
        Image image = new Image();
        course.setImage(image);
        image.setCourse(course);
        course = courseRepository.save(course);
        return mapper.toDto(course);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        courseDto.setImage(null);
        Course course = courseRepository.findById(courseDto.getId())
                .orElseThrow(() -> new NotFoundException(Course.class, courseDto.getId()));
        if (!Utils.isCourseOpenForEdit(course)) {
            throw new ConflictException(Course.class, ActionConflict.UPDATE, "Cannot update course when course status is " + course.getStatus(), course.getId());
        }
        User user = userService.getCurrentUser();
        if (!Objects.equals(courseDto.getVersion(), course.getVersion())) {
            throw new EntityOptimisticLockingException(course, course.getId());
        }
        if (ObjectUtils.notEqual(user.getId(), course.getMentor().getId())) {
            throw new ForbiddenException(Course.class, ActionConflict.UPDATE, "User does not own this course", user.getId());
        }

        // update field
        if (!course.getField().getId().equals(courseDto.getField().getId())) {
            Field field = fieldRepository.findById(courseDto.getField().getId())
                    .orElseThrow(() -> new NotFoundException(Field.class, courseDto.getField().getId()));
            course.setField(field);
        }

        mapper.toEntity(courseDto, course);
        course = courseRepository.save(course);
        return mapper.toDto(course);
    }

    @Override
    public void deleteCourse(UUID id) {
        // only delete course when course has not been deleted and there are no clazz in this course
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Course.class, id));
        if (course.getStatus() != CourseStatus.DISABLE) {
            if (Utils.isCourseOpenForEdit(course)) {
                // DRAFTING and OPEN guarantee course has no clazz
                courseRepository.delete(course);
            } else {
                long referenceCount = clazzRepository.countByCourse(course);
                if (referenceCount != 0) {
                    course.setStatus(CourseStatus.DISABLE);
                    courseRepository.save(course);
                } else {
                    courseRepository.delete(course);
                }
            }
        }
    }

    @Override
    public CourseDto openCourse(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Course.class, id));
        if (course.getStatus() != CourseStatus.DRAFTING) {
            throw new ConflictException(Course.class, ActionConflict.UPDATE, "Cannot change course status to OPEN from " + course.getStatus(), id);
        }
        course.setStatus(CourseStatus.OPEN);
        course = courseRepository.save(course);
        return mapper.toDto(course);
    }

    @Override
    public CourseDto disableCourse(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Course.class, id));
        if (course.getStatus() == CourseStatus.DRAFTING) {
            throw new ConflictException(Course.class, ActionConflict.UPDATE, "Cannot change course status to DISABLE from " + course.getStatus(), id);
        }
        course.setStatus(CourseStatus.DISABLE);
        course = courseRepository.save(course);
        return mapper.toDto(course);
    }

    @Override
    public List<ClazzDto> getClazzesByCourse(UUID id) {
        // TODO: pagination and maybe searching, filtering, sorting
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Course.class, id));

        if (!isCourseCanBeSeenByAll(course)) {
            User user = userService.getCurrentUser();
            if (!course.getMentor().getId().equals(user.getId())) {
                log.info("Unauthorized access to course. User ID {}", user.getId());
                throw new NotFoundException(Course.class, course.getId());
            }
        }

        return course.getClazzes().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<SessionDto> getSessionsOfCourse(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(Course.class, courseId));
        return course.getSessions().stream()
                .sorted(Comparator.comparing(Session::getSessionNum))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<CourseDto> getCourseOfStudent() {
        User user = userService.getCurrentUser();
        return courseRepository.getCourseOfStudent(user.getId()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<CourseDto> getCourseOfMentor() {
        User user = userService.getCurrentUser();
        return courseRepository.getCourseByMentor(user).stream()
                .map(mapper::toDto)
                .toList();
    }

    private boolean isCourseCanBeSeenByAll(Course course) {
        return course.getStatus() != CourseStatus.DRAFTING && course.getStatus() != CourseStatus.DISABLE;
    }
}
