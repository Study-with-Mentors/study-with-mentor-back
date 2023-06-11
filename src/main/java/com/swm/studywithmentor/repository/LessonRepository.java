package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.Lesson;
import com.swm.studywithmentor.repository.custom.LessonRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID>, QuerydslPredicateExecutor<Lesson>, LessonRepositoryCustom {
}
