package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.swm.studywithmentor.model.dto.search.MentorSearchDto;
import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.enrollment.QEnrollment;
import com.swm.studywithmentor.model.entity.user.QUser;
import com.swm.studywithmentor.model.entity.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findUserEnrollInCourse(UUID courseId) {
        var query = new JPAQuery<User>(entityManager);
        QEnrollment enrollment = QEnrollment.enrollment;
        return query.select(enrollment.student)
                .from(enrollment)
                .where(enrollment.status.eq(EnrollmentStatus.ENROLLED))
                .where(enrollment.clazz.course.id.eq(courseId))
                .fetch();
    }

    @Override
    public Predicate prepareSearchPredicate(MentorSearchDto searchDto) {
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.courses.isNotEmpty());
        if (StringUtils.isNotBlank(searchDto.getName())) {
            builder.and(user.firstName.concat(" ").concat(user.lastName)
                    .contains(searchDto.getName()));
        }
        if (!CollectionUtils.isEmpty(searchDto.getFields())) {
            builder.and(user.mentor.field.code.in(searchDto.getFields()));
        }
        if (!CollectionUtils.isEmpty(searchDto.getDegrees())) {
            builder.and(user.mentor.degree.in(searchDto.getDegrees()));
        }

        Predicate predicate = builder.getValue();
        if (predicate == null) {
            predicate = Expressions.asBoolean(true).isTrue();
        }
        return predicate;
    }
}
