package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.UserProfileDto;
import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.dto.search.LessonTimeRangeDto;
import com.swm.studywithmentor.model.dto.search.MentorSearchDto;
import com.swm.studywithmentor.model.entity.user.Role;
import com.swm.studywithmentor.service.ClazzService;
import com.swm.studywithmentor.service.CourseService;
import com.swm.studywithmentor.service.ImageService;
import com.swm.studywithmentor.service.LessonService;
import com.swm.studywithmentor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class StudentMentorController {
    private final LessonService lessonService;
    private final ClazzService clazzService;
    private final CourseService courseService;
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public StudentMentorController(LessonService lessonService, ClazzService clazzService, CourseService courseService, UserService userService, ImageService imageService) {
        this.lessonService = lessonService;
        this.clazzService = clazzService;
        this.courseService = courseService;
        this.userService = userService;
        this.imageService = imageService;
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

    @GetMapping("/mentor/{id}")
    public ResponseEntity<UserProfileDto> getMentorProfile(@PathVariable UUID id) {
        UserProfileDto dto = userService.getMentorProfile(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/mentor")
    public ResponseEntity<PageResult<UserProfileDto>> searchMentor(MentorSearchDto searchDto) {
        PageResult<UserProfileDto> dtos = userService.searchMentors(searchDto);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/mentor/{id}/image")
    public ResponseEntity<ImageDto> getMentorImage(@PathVariable UUID id) {
        ImageDto imageDto = imageService.getMentorImage(id);
        return new ResponseEntity<>(imageDto, HttpStatus.OK);
    }
}
