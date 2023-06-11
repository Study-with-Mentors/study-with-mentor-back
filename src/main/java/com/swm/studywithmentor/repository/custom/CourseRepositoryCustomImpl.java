package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.swm.studywithmentor.model.dto.search.CourseSearchDto;
import com.swm.studywithmentor.model.entity.course.QCourse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {
    @Override
    public Predicate prepareSearchPredicate(CourseSearchDto courseSearchDto) {
        QCourse course = QCourse.course;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotBlank(courseSearchDto.getName())) {
            String name = courseSearchDto.getName();
            builder.and(course.fullName.contains(name)
                    .or(course.shortName.contains(name)));
        }
        if (StringUtils.isNotBlank(courseSearchDto.getIntendedLearner())) {
            builder.and(course.intendedLearner.contains(courseSearchDto.getIntendedLearner()));
        }
        if (courseSearchDto.getCourseLevel() != null) {
            builder.and(course.courseLevel.eq(courseSearchDto.getCourseLevel()));
        }
        if (courseSearchDto.getFieldNames() != null && !courseSearchDto.getFieldNames().isEmpty()) {
            builder.and(course.field.code.in(courseSearchDto.getFieldNames()));
        }
        if (courseSearchDto.getMentorId() != null) {
            builder.and(course.mentor.id.eq(courseSearchDto.getMentorId()));
        }

        Predicate predicate = builder.getValue();
        if (predicate == null) {
            predicate = Expressions.asBoolean(true).isTrue();
        }
        return predicate;
    }
}
