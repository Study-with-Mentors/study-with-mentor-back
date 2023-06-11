package com.swm.studywithmentor.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ClazzCreateDto {
    private Date startDate;
    private Date endDate;
    private Date enrollmentEndDate;
    @NotNull
    @Min(0)
    private float price;
    @NotNull
    private UUID courseId;
    @NotNull
    private List<LessonCreateDto> lessonCreateDtos;
}
