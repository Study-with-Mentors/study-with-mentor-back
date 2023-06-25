package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.repository.custom.SessionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID>, SessionRepositoryCustom {
    List<Session> findByCourseId(UUID courseId);
}
