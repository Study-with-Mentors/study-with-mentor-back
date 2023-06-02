package com.swm.studywithmentor.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.swm.studywithmentor.model.entity.QField;
import com.swm.studywithmentor.model.entity.course.QCourse;
import com.swm.studywithmentor.model.entity.user.QMentor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class FieldRepositoryCustomImpl implements FieldRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long countFieldReference(UUID id) {
        QField field = QField.field;
        var query = new JPAQuery<>(entityManager);
        long courseRefCount = query.from(QCourse.course)
                .where(QCourse.course.field.id.eq(id))
                .stream().count();
        long mentorRefCount = query.from(QMentor.mentor)
                .where(QMentor.mentor.field.id.eq(id))
                .stream().count();

        return courseRefCount + mentorRefCount;
    }
}
