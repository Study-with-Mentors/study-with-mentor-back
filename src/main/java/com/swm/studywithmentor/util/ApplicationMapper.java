package com.swm.studywithmentor.util;

import com.swm.studywithmentor.model.dto.*;
import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.model.entity.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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

    public UserDto toDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User toEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    public void toEntity(UserDto userDto, User user) {
        mapper.map(userDto, user);
    }

    public SessionDto toDto(Session session) {
        SessionDto sessionDto = mapper.map(session, SessionDto.class);
        Set<ActivityDto> activityDtos = session.getActivities()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
        sessionDto.setActivities(activityDtos);
        sessionDto.setCourse(toDto(session.getCourse()));
        return sessionDto;
    }

    public Session toEntity(SessionDto sessionDto) {
        return mapper.map(sessionDto, Session.class);
    }

    public void toEntity(SessionDto sessionDto, Session session) {
        mapper.map(sessionDto, session);
    }

    public ActivityDto toDto(Activity activity) {
        return mapper.map(activity, ActivityDto.class);
    }

    public Activity toEntity(ActivityDto activityDto) {
        return mapper.map(activityDto, Activity.class);
    }

    public void toEntity(ActivityDto activityDto, Activity activity) {
        mapper.map(activityDto, activity);
    }
}
