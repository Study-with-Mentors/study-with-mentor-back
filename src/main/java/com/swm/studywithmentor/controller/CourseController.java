package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.SessionDto;
import com.swm.studywithmentor.model.dto.create.CourseCreateDto;
import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.dto.search.CourseSearchDto;
import com.swm.studywithmentor.service.CourseService;
import com.swm.studywithmentor.service.ImageService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final ImageService imageService;

    @Autowired
    public CourseController(CourseService courseService, ImageService imageService) {
        this.courseService = courseService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable UUID id) {
        CourseDto dto = courseService.getCourseById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}/classes")
    public ResponseEntity<List<ClazzDto>> getClazzFromCourse(@PathVariable UUID id) {
        List<ClazzDto> dtos = courseService.getClazzesByCourse(id);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ImageDto> getImage(@PathVariable UUID id) {
        ImageDto dto = imageService.getCourseImage(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}/sessions")
    public List<SessionDto> getSessions(@PathVariable UUID id) {
        return courseService.getSessionsOfCourse(id);
    }

    @GetMapping
    public PageResult<CourseDto> searchCourses(CourseSearchDto courseSearchDto) {
        return courseService.searchCourses(courseSearchDto, false);
    }

    @GetMapping("/visible")
    public ResponseEntity<PageResult<CourseDto>> searchVisibleCourses(CourseSearchDto courseSearchDto) {
        return new ResponseEntity<>(courseService.searchCourses(courseSearchDto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseCreateDto courseDto) {
        CourseDto resultDto = courseService.createCourse(courseDto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto, @PathVariable UUID id) {
        courseDto.setId(id);
        CourseDto resultDto = courseService.updateCourse(courseDto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<ImageDto> updateCourseImage(@PathVariable UUID id, @Valid @RequestBody ImageDto dto) {
        ImageDto resultDto = imageService.updateCourseImage(id, dto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/open")
    public ResponseEntity<CourseDto> openCourse(@PathVariable UUID id) {
        CourseDto resultDto = courseService.openCourse(id);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<CourseDto> disableCourse(@PathVariable UUID id) {
        CourseDto resultDto = courseService.disableCourse(id);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
    }
}
