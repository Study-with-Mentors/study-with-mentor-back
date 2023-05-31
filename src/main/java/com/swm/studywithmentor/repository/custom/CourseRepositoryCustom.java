package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.search.CourseSearchDto;

public interface CourseRepositoryCustom {
    Predicate prepareSearchPredicate(CourseSearchDto courseSearchDto);
}
