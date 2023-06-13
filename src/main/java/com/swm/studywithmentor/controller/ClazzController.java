package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.dto.create.ClazzCreateDto;
import com.swm.studywithmentor.model.dto.search.ClazzSearchDto;
import com.swm.studywithmentor.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clazz")
public class ClazzController {
    private final ClazzService clazzService;

    @Autowired
    public ClazzController(ClazzService clazzService) {
        this.clazzService = clazzService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClazzDto> getClazz(@PathVariable UUID id) {
        return new ResponseEntity<>(clazzService.getClazz(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClazzDto> startNewClazz(@Valid @RequestBody ClazzCreateDto clazzDto) {
        return new ResponseEntity<>(clazzService.startNewClazz(clazzDto), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ClazzDto>> getClazzByCourse(@PathVariable UUID courseId) {
        return new ResponseEntity<>(clazzService.getClazzesByCourse(courseId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResult<ClazzDto>> searchClazzes(ClazzSearchDto clazzSearchDto) {
        return new ResponseEntity<>(clazzService.searchClazzes(clazzSearchDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClazzDto> updateClazz(@PathVariable UUID id, @Valid @RequestBody ClazzDto clazzDto) {
        clazzDto.setId(id);
        return new ResponseEntity<>(clazzService.updateClazz(clazzDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClazz(@PathVariable UUID id) {
        clazzService.deleteClazz(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ClazzDto> closeClazz(@PathVariable UUID id) {
        return new ResponseEntity<>(clazzService.closeEnrollment(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/student")
    public ResponseEntity<List<UserDto>> getEnrolledStudent(@PathVariable UUID id) {
        return new ResponseEntity<>(clazzService.getEnrolledStudent(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/clazz")
    public ResponseEntity<List<LessonDto>> getClazzFromCourse(@PathVariable UUID id) {
        return new ResponseEntity<>(clazzService.getClazzFromCourse(id), HttpStatus.OK);
    }
}
