package com.swm.studywithmentor.model.dto.search;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class TimeRangeDto {
    private Date startDate;
    private Date endDate;
}
