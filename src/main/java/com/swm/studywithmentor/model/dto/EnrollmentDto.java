package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto extends BaseDto {
    private Date enrollmentDate;
    private EnrollmentStatus status;
    private UUID invoice;
}
