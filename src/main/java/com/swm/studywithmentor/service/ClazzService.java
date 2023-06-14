package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.dto.create.ClazzCreateDto;
import com.swm.studywithmentor.model.dto.search.ClazzSearchDto;

import java.util.List;
import java.util.UUID;

public interface ClazzService {
    ClazzDto startNewClazz(ClazzCreateDto clazzDto);

    ClazzDto getClazz(UUID id);

    PageResult<ClazzDto> searchClazzes(ClazzSearchDto dto);

    ClazzDto updateClazz(ClazzDto clazzDto);

    void deleteClazz(UUID id);

    ClazzDto closeEnrollment(UUID id);

    List<UserDto> getEnrolledStudent(UUID id);

    List<LessonDto> getClazzFromCourse(UUID id);
}
