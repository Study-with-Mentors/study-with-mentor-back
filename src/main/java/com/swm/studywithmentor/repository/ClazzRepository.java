package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClazzRepository extends JpaRepository<Clazz, UUID> {
    long countByCourse(Course course);
}
