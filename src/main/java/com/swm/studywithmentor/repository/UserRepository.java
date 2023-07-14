package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>,
    QuerydslPredicateExecutor<User>,
        UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    List<User> findAllByNotificationTokenNotNull();
}
