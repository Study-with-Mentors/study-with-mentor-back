package com.swm.studywithmentor.model.dto.create;

import com.swm.studywithmentor.model.entity.session.SessionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SessionCreateDto {
    @NotNull
    private long sessionNum;
    @NotEmpty
    private String sessionName;
    @NotNull
    private SessionType type;
    @NotEmpty
    private String description;
    private String resource;
    @NotNull
    private UUID courseId;
    private List<ActivityCreateDto> activities;
}
