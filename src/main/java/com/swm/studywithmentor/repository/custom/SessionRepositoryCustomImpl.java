package com.swm.studywithmentor.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.session.QSession;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SessionRepositoryCustomImpl implements SessionRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long findMaxSessionNum(Course course) {
        QSession session = QSession.session;
        var query = new JPAQuery<Long>(entityManager);
        return query.select(session.sessionNum.max())
                .from(session)
                .where(session.course.id.eq(course.getId()))
                .fetchFirst();
    }

    @Override
    public void decreaseSessionNum(Course course, long lower) {
        QSession session = QSession.session;
        var queryFactory = new JPAQueryFactory(entityManager);
        queryFactory.update(session)
                .where(session.course.id.eq(course.getId()))
                .where(session.sessionNum.goe(lower))
                .set(session.sessionNum, session.sessionNum.subtract(1))
                .execute();
    }

    @Override
    public void increaseSessionNum(Course course, long lower) {
        QSession session = QSession.session;
        var queryFactory = new JPAQueryFactory(entityManager);
        queryFactory.update(session)
                .where(session.course.id.eq(course.getId()))
                // E.g session Num: 1 2 3 4 5. Insert one to num 1 will move the previous to 2 3 4 5
                .where(session.sessionNum.goe(lower))
                .set(session.sessionNum, session.sessionNum.add(1))
                .execute();
    }

    @Override
    public void decreaseSessionNum(Course course, long lower, long upper) {
        QSession session = QSession.session;
        var queryFactory = new JPAQueryFactory(entityManager);
        queryFactory.update(session)
                .where(session.course.id.eq(course.getId()))
                .where(session.sessionNum.gt(lower))
                .where(session.sessionNum.loe(upper))
                .set(session.sessionNum, session.sessionNum.subtract(1))
                .execute();
    }

    @Override
    public void increaseSessionNum(Course course, long lower, long upper) {
        QSession session = QSession.session;
        var queryFactory = new JPAQueryFactory(entityManager);
        queryFactory.update(session)
                .where(session.course.id.eq(course.getId()))
                .where(session.sessionNum.goe(lower))
                .where(session.sessionNum.lt(upper))
                .set(session.sessionNum, session.sessionNum.add(1))
                .execute();
    }
}
