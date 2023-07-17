package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.ClazzDto;
import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.model.dto.EnrollmentReportDto;
import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.UserProfileDto;
import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.dto.search.LessonTimeRangeDto;
import com.swm.studywithmentor.model.dto.search.MentorSearchDto;
import com.swm.studywithmentor.model.dto.search.TimeRangeDto;
import com.swm.studywithmentor.model.entity.user.Role;
import com.swm.studywithmentor.service.ClazzService;
import com.swm.studywithmentor.service.CourseService;
import com.swm.studywithmentor.service.EnrollmentService;
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
    private final EnrollmentService enrollmentService;

    @Autowired
    public StudentMentorController(LessonService lessonService, ClazzService clazzService, CourseService courseService, UserService userService, ImageService imageService, EnrollmentService enrollmentService) {
        this.lessonService = lessonService;
        this.clazzService = clazzService;
        this.courseService = courseService;
        this.userService = userService;
        this.imageService = imageService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/me/student/lessons")
    public ResponseEntity<List<LessonDto>> getLessonInTimeRangeStudent(LessonTimeRangeDto dto) {
        dto.setRole(Role.USER);
        return new ResponseEntity<>(lessonService.getLessonByTimeRange(dto), HttpStatus.OK);
    }

    @GetMapping("/me/student/classes")
    public ResponseEntity<List<ClazzDto>> getStudentClazz() {
        List<ClazzDto> dtos = clazzService.getClazzOfStudent();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/me/student/courses")
    public ResponseEntity<List<CourseDto>> getStudentEnrolledCourse() {
        List<CourseDto> dtos = courseService.getCourseOfStudent();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/me/mentor/lessons")
    public ResponseEntity<List<LessonDto>> getLessonInTimeRangeMentor(LessonTimeRangeDto dto) {
        dto.setRole(Role.MENTOR);
        return new ResponseEntity<>(lessonService.getLessonByTimeRange(dto), HttpStatus.OK);
    }

    @GetMapping("/me/mentor/classes")
    public ResponseEntity<List<ClazzDto>> getMentorClazz() {
        List<ClazzDto> dtos = clazzService.getClazzOfMentor();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/me/mentor/courses")
    public ResponseEntity<List<CourseDto>> getMentorCourse() {
        List<CourseDto> dtos = courseService.getCourseOfMentor();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/me/mentor/enrollments/report")
    public EnrollmentReportDto getEnrollmentReport(TimeRangeDto timeRangeDto) {
        return enrollmentService.getEnrollmentReport(timeRangeDto);
    }

    @GetMapping("/mentors/{id}")
    public ResponseEntity<UserProfileDto> getMentorProfile(@PathVariable UUID id) {
        UserProfileDto dto = userService.getMentorProfile(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/mentors")
    public ResponseEntity<PageResult<UserProfileDto>> searchMentor(MentorSearchDto searchDto) {
        PageResult<UserProfileDto> dtos = userService.searchMentors(searchDto);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/mentors/{id}/image")
    public ResponseEntity<ImageDto> getMentorImage(@PathVariable UUID id) {
        ImageDto imageDto = imageService.getMentorImage(id);
        return new ResponseEntity<>(imageDto, HttpStatus.OK);
    }
}
