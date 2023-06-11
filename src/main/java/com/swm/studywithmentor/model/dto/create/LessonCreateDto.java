package com.swm.studywithmentor.model.dto.create;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    // FIXME: change the timezone of timestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp endTime;
    @NotEmpty
    private String location;
    @NotNull
    private UUID sessionId;
}
