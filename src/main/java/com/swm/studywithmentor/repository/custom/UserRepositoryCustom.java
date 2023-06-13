package com.swm.studywithmentor.repository.custom;

import com.swm.studywithmentor.model.entity.user.User;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryCustom {
    List<User> findUserEnrollInCourse(UUID courseId);
}
