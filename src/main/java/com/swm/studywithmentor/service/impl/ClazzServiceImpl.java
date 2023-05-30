package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.service.ClazzService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClazzServiceImpl implements ClazzService {
    private final ClazzRepository clazzRepository;
    private final CourseRepository courseRepository;
    private final ApplicationMapper mapper;

    @Autowired
    public ClazzServiceImpl(ClazzRepository clazzRepository, CourseRepository courseRepository, ApplicationMapper mapper) {
        this.clazzRepository = clazzRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public ClazzDto startNewClazz(ClazzDto clazzDto) {
        if (clazzDto.getStartDate().after(clazzDto.getEndDate())) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "Start date must be before end date", clazzDto.getStartDate(), clazzDto.getEndDate());
        } else if (clazzDto.getEnrollmentEndDate().after(clazzDto.getStartDate())) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "Enrollment end date must be before start date", clazzDto.getEnrollmentEndDate(), clazzDto.getStartDate());
        }
        Clazz clazz = mapper.toEntity(clazzDto);
        Course course = courseRepository.findById(clazzDto.getCourseId())
                .orElseThrow(() -> new NotFoundException(Course.class, clazzDto.getCourseId()));

        if (course.getStatus() == CourseStatus.DISABLE) {
            throw new ConflictException(Clazz.class, ActionConflict.CREATE, "Course is not possible to create new class", course.getId());
        }
        // TODO: should i create clazz as the time with lesson
        // TODO: validate clazz lesson time not duplicate with other clazz of the mentor's courses
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
                .collect(Collectors.toList());
    }

    @Override
    public List<ClazzDto> getClazzes() {
        return null;
    }

    @Override
    public ClazzDto updateClazz(ClazzDto clazzDto) {
        return null;
    }

    @Override
    public void deleteClazz(UUID id) {
        // TODO: only delete when there is no enrollment and maybe in one week after created
    }
}
