package com.swm.studywithmentor.util;

import com.swm.studywithmentor.model.dto.CourseDto;
import com.swm.studywithmentor.model.dto.FieldDto;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.course.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {
    private final ModelMapper mapper;

    @Autowired
    public ApplicationMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CourseDto toDto(Course course) {
        CourseDto courseDto = mapper.map(course, CourseDto.class);
        courseDto.setField(toDto(course.getField()));
        // TODO: field mentor
        return courseDto;
    }

    public Course toEntity(CourseDto courseDto) {
        return mapper.map(courseDto, Course.class);
    }

    public void toEntity(CourseDto courseDto, Course course) {
        mapper.map(courseDto, course);
    }

    public FieldDto toDto(Field field) {
        return mapper.map(field, FieldDto.class);
    }

    public Field toEntity(FieldDto fieldDto) {
        return mapper.map(fieldDto, Field.class);
    }

    public void toEntity(FieldDto fieldDto, Field field) {
        mapper.map(fieldDto, field);
    }
}
