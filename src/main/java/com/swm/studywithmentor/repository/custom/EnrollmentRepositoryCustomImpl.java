package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.swm.studywithmentor.model.dto.search.TimeRangeDto;
import com.swm.studywithmentor.model.entity.course.QCourse;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.enrollment.QEnrollment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class EnrollmentRepositoryCustomImpl implements EnrollmentRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Enrollment> getEnrollmentInTimeRange(TimeRangeDto dto, UUID mentorId) {
        QEnrollment enrollment = QEnrollment.enrollment;
        QCourse course = QCourse.course;
        var query = new JPAQuery<Enrollment>(entityManager);
        BooleanBuilder builder = new BooleanBuilder();
        if (dto.getStartDate() != null) {
            builder.and(enrollment.enrollmentDate.after(dto.getStartDate()));
        }
        if (dto.getEndDate() != null) {
            builder.and(enrollment.enrollmentDate.before(dto.getEndDate()));
        }
        builder.and(enrollment.clazz.course.id.in(
                JPAExpressions.select(course.id)
                        .from(course)
                        .where(course.mentor.id.eq(mentorId))
        ));
        Predicate predicate = builder.getValue();

        return query.from(enrollment)
                .where(predicate)
                .fetch();
    }
}
