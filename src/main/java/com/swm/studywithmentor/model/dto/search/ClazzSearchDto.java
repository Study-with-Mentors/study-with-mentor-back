package com.swm.studywithmentor.model.dto.search;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
/*
Clazz search will include course search and more fields to it
 */
public class ClazzSearchDto extends CourseSearchDto {
    private Date lowerStartDate;
    private Date upperStartDate;
    private Date lowerEndDate;
    private Date upperEndDate;
    private Date lowerEnrollmentDate;
    private Date upperEnrollmentDate;
    private Float lowerPrice;
    private Float upperPrice;
}
