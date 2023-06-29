package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.swm.studywithmentor.model.dto.search.ClazzSearchDto;
import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.QClazz;
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
public class ClazzRepositoryCustomImpl implements ClazzRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Predicate prepareSearchPredicate(ClazzSearchDto searchDto) {
        QClazz clazz = QClazz.clazz;
        QCourse course = QClazz.clazz.course;
        BooleanBuilder builder = new BooleanBuilder();

        // course part
        builder.and(course.status.ne(CourseStatus.DISABLE));
        if (StringUtils.isNotBlank(searchDto.getName())) {
            String name = searchDto.getName();
            builder.and(course.fullName.contains(name)
                    .or(course.shortName.contains(name)));
        }
        if (searchDto.getIntendedLearner() != null) {
            builder.and(course.intendedLearner.eq(searchDto.getIntendedLearner()));
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

    @Override
    public List<Clazz> getClazzOfStudent(UUID studenId) {
        QClazz clazz = QClazz.clazz;
        QEnrollment enrollment = QEnrollment.enrollment;
        var query = new JPAQuery<Clazz>(entityManager);
        return query.from(clazz)
                .where(clazz.id.in(
                        JPAExpressions.select(enrollment.clazz.id)
                                .from(enrollment)
                                .where(enrollment.status.eq(EnrollmentStatus.ENROLLED))
                                .where(enrollment.student.id.eq(studenId))
                ))
                .fetch();
    }

    @Override
    public List<Clazz> getClazzOfMentor(UUID mentorId) {
        QClazz clazz = QClazz.clazz;
        var query = new JPAQuery<Clazz>(entityManager);
        return query.from(clazz)
                .where(clazz.course.mentor.id.eq(mentorId))
                .fetch();
    }
}
