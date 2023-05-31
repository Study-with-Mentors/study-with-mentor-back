package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.FieldDto;
import com.swm.studywithmentor.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/field")
public class FieldController {
    private final FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDto> getField(@PathVariable UUID id) {
        FieldDto dto = fieldService.getFieldById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FieldDto>> getFields(@RequestParam(required = false, defaultValue = "") String name) {
        List<FieldDto> dtos = fieldService.searchFields(name);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FieldDto> createField(@Validated @RequestBody FieldDto dto) {
        FieldDto resultDto = fieldService.createField(dto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldDto> updateField(@Validated @RequestBody FieldDto dto, @PathVariable UUID id) {
        dto.setId(id);
        FieldDto resultDto = fieldService.updateField(dto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable UUID id) {
        fieldService.deleteField(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
