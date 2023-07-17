package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.swm.studywithmentor.model.dto.search.CourseSearchDto;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.entity.course.QCourse;
import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.enrollment.QEnrollment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Predicate prepareSearchPredicate(CourseSearchDto courseSearchDto, boolean visibleOnly) {
        QCourse course = QCourse.course;
        BooleanBuilder builder = new BooleanBuilder();
        if (visibleOnly) {
            builder.and(course.status.notIn(CourseStatus.DRAFTING, CourseStatus.DISABLE));
        }
        if (StringUtils.isNotBlank(courseSearchDto.getName())) {
            String name = courseSearchDto.getName();
            builder.and(course.fullName.contains(name)
                    .or(course.shortName.contains(name)));
        }
        if (courseSearchDto.getIntendedLearner() != null) {
            builder.and(course.intendedLearner.eq(courseSearchDto.getIntendedLearner()));
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

    @Override
    public List<Course> getCourseOfStudent(UUID studentId) {
        QCourse course = QCourse.course;
        QEnrollment enrollment = QEnrollment.enrollment;
        var query = new JPAQuery<Course>(entityManager);
        return query.from(course)
                .where(course.id.in(
                        JPAExpressions.select(enrollment.clazz.course.id)
                                .from(enrollment)
                                .where(enrollment.status.eq(EnrollmentStatus.ENROLLED))
                                .where(enrollment.student.id.eq(studentId))
                ))
                .fetch();
    }
}
