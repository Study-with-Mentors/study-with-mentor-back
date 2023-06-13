package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.repository.custom.ClazzRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ClazzRepository extends JpaRepository<Clazz, UUID>, QuerydslPredicateExecutor<Clazz>, ClazzRepositoryCustom {
    long countByCourse(Course course);
}
