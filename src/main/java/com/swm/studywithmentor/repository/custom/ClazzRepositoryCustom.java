package com.swm.studywithmentor.repository.custom;

import com.querydsl.core.types.Predicate;
import com.swm.studywithmentor.model.dto.search.ClazzSearchDto;
import com.swm.studywithmentor.model.entity.Clazz;

import java.util.List;
import java.util.UUID;

public interface ClazzRepositoryCustom {
    Predicate prepareSearchPredicate(ClazzSearchDto searchDto);
    List<Clazz> getClazzOfStudent(UUID studenId);
    List<Clazz> getClazzOfMentor(UUID mentorId);
}
