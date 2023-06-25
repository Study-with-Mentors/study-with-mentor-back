package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.repository.custom.CourseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>,
    QuerydslPredicateExecutor<Course>,
    CourseRepositoryCustom {
    List<Course> getCourseByMentor(User mentor);
}
