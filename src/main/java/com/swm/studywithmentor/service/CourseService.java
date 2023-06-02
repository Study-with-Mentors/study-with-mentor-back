package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.search.CourseSearchDto;

import java.util.UUID;

public interface CourseService {
    CourseDto getCourseById(UUID id);
    PageResult<CourseDto> searchCourses(CourseSearchDto courseSearchDto);
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(CourseDto courseDto);
    void deleteCourse(UUID id);
}
