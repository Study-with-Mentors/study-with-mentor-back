package com.swm.studywithmentor.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class ClazzDto extends BaseDto {
    private Date startDate;
    private Date endDate;
    private Date enrollmentEndDate;
    @NotNull
    @Min(0)
    private float price;
    @NotNull
    private CourseDto course;
}
