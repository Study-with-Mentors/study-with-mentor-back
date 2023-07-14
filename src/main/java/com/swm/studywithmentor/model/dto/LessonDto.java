package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.dto.create.ImageDto;
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
    private String courseName;
    private String sessionName;
    private UUID clazzId;
    private UUID courseId;
    private UUID sessionId;
    private String resource;
    private ImageDto courseImage;
}
