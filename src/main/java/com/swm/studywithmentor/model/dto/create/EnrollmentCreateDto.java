package com.swm.studywithmentor.model.dto.create;

import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import lombok.Data;

import java.util.UUID;

@Data
public class EnrollmentCreateDto {
    UUID studentId;
    UUID classId;
    PaymentType paymentType;
}
