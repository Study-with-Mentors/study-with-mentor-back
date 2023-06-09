package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.ClazzStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
public class ClazzDto extends BaseDto {
    private Date startDate;
    private Date endDate;
    private Date enrollmentEndDate;
    private ClazzStatus status;
    @NotNull
    @Min(0)
    private float price;
    @NotNull
    private UUID courseId;
}
