package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.search.MentorSearchDto;
import com.swm.studywithmentor.model.entity.user.User;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryCustom {
    List<User> findUserEnrollInCourse(UUID courseId);

    Predicate prepareSearchPredicate(MentorSearchDto searchDto);
}
