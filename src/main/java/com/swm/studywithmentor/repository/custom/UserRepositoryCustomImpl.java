package com.swm.studywithmentor.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.swm.studywithmentor.model.entity.user.QUser;
import com.swm.studywithmentor.model.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> findUserEnrollInCourse(UUID courseId) {
        var query = new JPAQuery<User>(entityManager);
        QUser user = QUser.user;
        return query.from(user)
                .where(user.enrollments.any().clazz.id.eq(courseId))
                .fetch();
    }
}
