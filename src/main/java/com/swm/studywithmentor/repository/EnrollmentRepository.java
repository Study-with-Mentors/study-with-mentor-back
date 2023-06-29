package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.repository.custom.EnrollmentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID>,
        JpaSpecificationExecutor<Enrollment>, EnrollmentRepositoryCustom {
    long countEnrollmentByClazz(Clazz clazz);
    Optional<Enrollment> findByClazzAndStudent(Clazz clazz, User student);
}
