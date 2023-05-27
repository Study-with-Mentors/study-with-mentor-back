package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.repository.custom.FieldRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID>, QuerydslPredicateExecutor<Field>, FieldRepositoryCustom {
}
