package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.invoice.InvoiceStatus;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private UUID invoiceId;
    private EnrollmentDto enrollment;
    private PaymentType paymentType;
    private Date payDate;
    private InvoiceStatus status;
    private float totalPrice;
}
