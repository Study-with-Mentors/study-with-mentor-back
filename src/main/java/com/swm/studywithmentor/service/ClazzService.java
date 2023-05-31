package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.ClazzDto;

import java.util.List;
import java.util.UUID;

public interface ClazzService {
    ClazzDto startNewClazz(ClazzDto clazzDto);
    ClazzDto getClazz(UUID id);
    List<ClazzDto> getClazzes();
    ClazzDto updateClazz(ClazzDto clazzDto);
    void deleteClazz(UUID id);
}
