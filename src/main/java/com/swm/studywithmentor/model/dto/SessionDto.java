package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.session.SessionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SessionDto extends BaseDto {
    @NotNull
    private long sessionNum;
    @NotEmpty
    private String sessionName;
    @NotNull
    private SessionType type;
    @NotEmpty
    private String description;
    private String resource;
    // TODO: might change courseDto to only courseId
    @NotNull
    private CourseDto course;
    private List<ActivityDto> activities;
}
