package com.swm.studywithmentor.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class LessonCreateDto {
    @NotNull
    @Min(0)
    private Long lessonNum;
    @NotNull
    private Timestamp startTime;
    @NotNull
    private Timestamp endTime;
    @NotEmpty
    private String location;
    @NotNull
    private UUID sessionId;
}
