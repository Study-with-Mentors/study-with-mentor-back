package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.CourseDto;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseDto getCourseById(UUID id);
    List<CourseDto> getCourses();
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(CourseDto courseDto);
    void deleteCourse(UUID id);
}
