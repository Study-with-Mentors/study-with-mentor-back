package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.FieldDto;
import com.swm.studywithmentor.model.dto.create.FieldCreateDto;

import java.util.List;
import java.util.UUID;

public interface FieldService {
    FieldDto getFieldById(UUID id);
    List<FieldDto> searchFields(String keyword);
    FieldDto createField(FieldCreateDto fieldDto);
    FieldDto updateField(FieldDto fieldDto);
    void deleteField(UUID id);
}
