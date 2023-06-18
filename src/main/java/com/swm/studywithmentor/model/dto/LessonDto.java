package com.swm.studywithmentor.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class LessonDto extends BaseDto {
    private long lessonNum;
    private Timestamp startTime;
    private Timestamp endTime;
    private String location;
    private UUID clazzId;
    private UUID sessionId;
}
