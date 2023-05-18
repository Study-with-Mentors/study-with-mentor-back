package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.Course;
import com.swm.studywithmentor.repository.custom.CourseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CourseRepository extends JpaRepository<Course, Long>,
    QuerydslPredicateExecutor<Course>,
    CourseRepositoryCustom {
}
