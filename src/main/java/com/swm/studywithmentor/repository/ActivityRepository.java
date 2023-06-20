package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID>, QuerydslPredicateExecutor<Activity> {
}
