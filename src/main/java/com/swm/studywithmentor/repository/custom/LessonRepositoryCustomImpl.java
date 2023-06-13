package com.swm.studywithmentor.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.swm.studywithmentor.model.entity.Lesson;
import com.swm.studywithmentor.model.entity.QLesson;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class LessonRepositoryCustomImpl implements LessonRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Lesson> findLessonBetweenTime(Timestamp startTime, Timestamp endTime, UUID mentorId) {
        var query = new JPAQuery<Lesson>(entityManager);
        QLesson lesson = QLesson.lesson;
        return query.from(lesson)
                .where(lesson.startTime.after(startTime).and(lesson.endTime.before(endTime)))
                .where(lesson.endTime.after(startTime).and(lesson.endTime.before(endTime)))
                .fetch();
    }
}
