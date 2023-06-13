package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.search.ClazzSearchDto;

public interface ClazzRepositoryCustom {
    Predicate prepareSearchPredicate(ClazzSearchDto searchDto);
}
