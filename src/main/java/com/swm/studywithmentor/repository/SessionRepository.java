package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByCourseId(UUID courseId);
}
