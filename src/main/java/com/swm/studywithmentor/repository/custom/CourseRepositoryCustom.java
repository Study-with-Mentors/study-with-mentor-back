package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.search.CourseSearchDto;
import com.swm.studywithmentor.model.entity.course.Course;

import java.util.List;
import java.util.UUID;

public interface CourseRepositoryCustom {
    Predicate prepareSearchPredicate(CourseSearchDto courseSearchDto);
    List<Course> getCourseOfStudent(UUID id);
}
