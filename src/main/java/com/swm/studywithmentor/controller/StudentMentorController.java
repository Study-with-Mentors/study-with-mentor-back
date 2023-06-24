package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.search.LessonTimeRangeDto;
import com.swm.studywithmentor.model.entity.user.Role;
import com.swm.studywithmentor.service.ClazzService;
import com.swm.studywithmentor.service.CourseService;
import com.swm.studywithmentor.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentMentorController {
    private final LessonService lessonService;
    private final ClazzService clazzService;
    private final CourseService courseService;

    @Autowired
    public StudentMentorController(LessonService lessonService, ClazzService clazzService, CourseService courseService) {
        this.lessonService = lessonService;
        this.clazzService = clazzService;
        this.courseService = courseService;
    }

    @GetMapping("/student/lesson")
    public ResponseEntity<List<LessonDto>> getLessonInTimeRangeStudent(LessonTimeRangeDto dto) {
        dto.setRole(Role.USER);
        return new ResponseEntity<>(lessonService.getLessonByTimeRange(dto), HttpStatus.OK);
    }

    @GetMapping("/student/clazz")
    public ResponseEntity<List<ClazzDto>> getStudentClazz() {
        List<ClazzDto> dtos = clazzService.getClazzOfStudent();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/student/course")
    public ResponseEntity<List<CourseDto>> getStudentEnrolledCourse() {
        List<CourseDto> dtos = courseService.getCourseOfStudent();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/mentor/lesson")
    public ResponseEntity<List<LessonDto>> getLessonInTimeRangeMentor(LessonTimeRangeDto dto) {
        dto.setRole(Role.MENTOR);
        return new ResponseEntity<>(lessonService.getLessonByTimeRange(dto), HttpStatus.OK);
    }

    @GetMapping("/mentor/clazz")
    public ResponseEntity<List<ClazzDto>> getMentorClazz() {
        List<ClazzDto> dtos = clazzService.getClazzOfMentor();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/mentor/course")
    public ResponseEntity<List<CourseDto>> getMentorCourse() {
        List<CourseDto> dtos = courseService.getCourseOfMentor();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
