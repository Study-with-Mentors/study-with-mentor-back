package com.swm.studywithmentor.service.impl;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.search.CourseSearchDto;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.model.exception.ExceptionErrorCodeConstants;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.FieldRepository;
import com.swm.studywithmentor.service.CourseService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ClazzRepository clazzRepository;
    private final FieldRepository fieldRepository;
    private final ApplicationMapper mapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ClazzRepository clazzRepository, FieldRepository fieldRepository, ApplicationMapper mapper) {
        this.courseRepository = courseRepository;
        this.clazzRepository = clazzRepository;
        this.fieldRepository = fieldRepository;
        this.mapper = mapper;
    }

    @Override
    public CourseDto getCourseById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionErrorCodeConstants.NOT_FOUND, HttpStatus.NOT_FOUND, "Course not found. Id: " + id));

        return mapper.toDto(course);
    }

    @Override
    public PageResult<CourseDto> searchCourses(CourseSearchDto courseSearchDto) {
        Predicate searchPredicate =  courseRepository.prepareSearchPredicate(courseSearchDto);
        // TODO: add page size to property file
        // TODO: add sorting to course search dto
        PageRequest pageRequest = PageRequest.of(courseSearchDto.getPage(), 20);
        Page<Course> courses = courseRepository.findAll(searchPredicate, pageRequest);
        PageResult<CourseDto> resultPage = new PageResult<>();
        resultPage.setResult(courses.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()));
        resultPage.setTotalPages(courses.getTotalPages());
        resultPage.setTotalElements(courses.getTotalElements());
        return resultPage;
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = mapper.toEntity(courseDto);
        course.setId(null);
        course.setStatus(CourseStatus.ENABLE);
        Field field = fieldRepository.findById(courseDto.getField().getId())
                .orElseThrow(() -> new NotFoundException(Field.class, courseDto.getField().getId()));
        // TODO: set mentor after implement user repository
        course.setField(field);
        course = courseRepository.save(course);
        return mapper.toDto(course);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        // TODO: check if the user owns this course or not
        Course course = courseRepository.findById(courseDto.getId())
                .orElseThrow(() -> new NotFoundException(Course.class, courseDto.getId()));

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
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Course.class, id));
        if (course.getStatus() != CourseStatus.DISABLE) {
            // TODO: test this one
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
