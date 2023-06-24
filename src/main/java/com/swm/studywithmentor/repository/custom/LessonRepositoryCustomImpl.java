package com.swm.studywithmentor.repository.custom;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.swm.studywithmentor.model.entity.ClazzStatus;
import com.swm.studywithmentor.model.entity.Lesson;
import com.swm.studywithmentor.model.entity.QLesson;
import com.swm.studywithmentor.model.entity.course.QCourse;
import com.swm.studywithmentor.model.entity.enrollment.QEnrollment;
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
    public List<Lesson> findLessonInTimeRange(Timestamp startTime, Timestamp endTime, UUID mentorId) {
        var query = new JPAQuery<Lesson>(entityManager);
        QLesson lesson = QLesson.lesson;
        QCourse course = QCourse.course;
        return query.from(lesson)
                .where(lesson.clazz.course.id.in(
                        JPAExpressions.select(course.id)
                                .from(course)
                                .where(course.mentor.id.eq(mentorId))
                ))
                .where(lesson.clazz.status.ne(ClazzStatus.CANCEL))
                .where(lesson.startTime.after(startTime).and(lesson.endTime.before(endTime)))
                .where(lesson.endTime.after(startTime).and(lesson.endTime.before(endTime)))
                .fetch();
    }

    @Override
    public List<Lesson> findLessonBetweenTimeAsStudent(Timestamp lowerTime, Timestamp upperTime, UUID studentId) {
        var query = new JPAQuery<Lesson>(entityManager);
        QLesson lesson = QLesson.lesson;
        return query.from(lesson)
                .where(lesson.clazz.enrollments.any().id.in(
                        JPAExpressions.select(QEnrollment.enrollment.id)
                                .from(QEnrollment.enrollment)
                                .where(QEnrollment.enrollment.student.id.eq(studentId))
                ))
                .where(lesson.clazz.status.ne(ClazzStatus.CANCEL))
                .where(lesson.startTime.after(lowerTime).and(lesson.endTime.before(upperTime)))
                .where(lesson.endTime.after(lowerTime).and(lesson.endTime.before(upperTime)))
                .fetch();
    }
}
