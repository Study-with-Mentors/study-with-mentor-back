package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID>,
        JpaSpecificationExecutor<Enrollment> {
    long countEnrollmentByClazz(Clazz clazz);
}
