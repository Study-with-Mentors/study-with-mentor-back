package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.FieldDto;

import java.util.List;
import java.util.UUID;

public interface FieldService {
    FieldDto getFieldById(UUID id);
    List<FieldDto> getFields();
    FieldDto createField(FieldDto fieldDto);
    FieldDto updateField(FieldDto fieldDto);
    void deleteField(UUID id);
}
