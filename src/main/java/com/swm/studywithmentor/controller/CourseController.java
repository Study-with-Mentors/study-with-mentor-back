package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable UUID id) {
        CourseDto dto = courseService.getCourseById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        CourseDto resultDto = courseService.createCourse(courseDto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto, @PathVariable UUID id) {
        courseDto.setId(id);
        CourseDto resultDto = courseService.updateCourse(courseDto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
    }
}
