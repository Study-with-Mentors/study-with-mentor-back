package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.FieldDto;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.FieldRepository;
import com.swm.studywithmentor.service.FieldService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final ApplicationMapper mapper;

    @Autowired
    public FieldServiceImpl(FieldRepository fieldRepository, ApplicationMapper mapper) {
        this.fieldRepository = fieldRepository;
        this.mapper = mapper;
    }

    @Override
    public FieldDto getFieldById(UUID id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Field.class, id));
        return mapper.toDto(field);
    }

    @Override
    public List<FieldDto> getFields() {
        // TODO: modify this into searching, pagination
        List<Field> fields = fieldRepository.findAll();
        return fields.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FieldDto createField(FieldDto fieldDto) {
        Field field = mapper.toEntity(fieldDto);
        field.setId(null);
        field = fieldRepository.save(field);
        return mapper.toDto(field);
    }

    @Override
    public FieldDto updateField(FieldDto fieldDto) {
        Field field = fieldRepository.findById(fieldDto.getId())
                .orElseThrow(() -> new NotFoundException(Field.class, fieldDto.getId()));
        mapper.toEntity(fieldDto, field);
        // FIXME: Optimistic locking is not working
        field = fieldRepository.save(field);
        return mapper.toDto(field);
    }

    @Override
    public void deleteField(UUID id) {
        // only delete when there are no reference to this field
        long referenceCount = fieldRepository.countFieldReference(id);
        if (referenceCount > 0) {
            // TODO: change this to ConflictException after merging session-crud
            throw new ApplicationException("", HttpStatus.CONFLICT, "Field is referenced elsewhere");
        }
        fieldRepository.deleteById(id);
    }
}
