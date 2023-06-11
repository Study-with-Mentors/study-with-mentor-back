package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.swm.studywithmentor.model.dto.search.ClazzSearchDto;
import com.swm.studywithmentor.model.entity.QClazz;
import com.swm.studywithmentor.model.entity.course.QCourse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ClazzRepositoryCustomImpl implements ClazzRepositoryCustom {
    @Override
    public Predicate prepareSearchPredicate(ClazzSearchDto searchDto) {
        QClazz clazz = QClazz.clazz;
        QCourse course = QClazz.clazz.course;
        BooleanBuilder builder = new BooleanBuilder();

        // course part
        if (StringUtils.isNotBlank(searchDto.getName())) {
            String name = searchDto.getName();
            builder.and(course.fullName.contains(name)
                    .or(course.shortName.contains(name)));
        }
        if (StringUtils.isNotBlank(searchDto.getIntendedLearner())) {
            builder.and(course.intendedLearner.contains(searchDto.getIntendedLearner()));
        }
        if (searchDto.getCourseLevel() != null) {
            builder.and(course.courseLevel.eq(searchDto.getCourseLevel()));
        }
        if (searchDto.getFieldNames() != null && !searchDto.getFieldNames().isEmpty()) {
            builder.and(course.field.code.in(searchDto.getFieldNames()));
        }
        if (searchDto.getMentorId() != null) {
            builder.and(course.mentor.id.eq(searchDto.getMentorId()));
        }

        // clazz part
        // start date range
        if (searchDto.getLowerStartDate() != null) {
            builder.and(clazz.startDate.after(searchDto.getLowerStartDate()));
        }
        if (searchDto.getUpperStartDate() != null) {
            builder.and(clazz.startDate.before(searchDto.getUpperStartDate()));
        }

        // end date range
        if (searchDto.getLowerEndDate() != null) {
            builder.and(clazz.endDate.after(searchDto.getLowerEndDate()));
        }
        if (searchDto.getUpperEndDate() != null) {
            builder.and(clazz.endDate.before(searchDto.getUpperEndDate()));
        }

        // enrollment end date range
        if (searchDto.getLowerEnrollmentDate() != null) {
            builder.and(clazz.enrollmentEndDate.after(searchDto.getLowerEnrollmentDate()));
        }
        if (searchDto.getUpperEnrollmentDate() != null) {
            builder.and(clazz.enrollmentEndDate.before(searchDto.getUpperEnrollmentDate()));
        }

        // price range
        if (searchDto.getLowerPrice() != null) {
            builder.and(clazz.price.goe(searchDto.getLowerPrice()));
        }
        if (searchDto.getUpperPrice() != null) {
            builder.and(clazz.price.loe(searchDto.getUpperPrice()));
        }

        Predicate predicate = builder.getValue();
        if (predicate == null) {
            predicate = Expressions.asBoolean(true).isTrue();
        }
        return predicate;
    }
}
