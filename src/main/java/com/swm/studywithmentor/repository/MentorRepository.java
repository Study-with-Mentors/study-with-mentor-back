package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.user.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MentorRepository extends JpaRepository<Mentor, UUID> {
}
