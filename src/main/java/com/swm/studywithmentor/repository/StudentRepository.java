package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
