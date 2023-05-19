package com.swm.studywithmentor.model.entity.invoice;

import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Column
    @Version
    private Long version;

    @Id
    private Long invoiceId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "invoice_id")
    private Enrollment enrollment;
    @Enumerated(EnumType.ORDINAL)
    private PaymentType type;
    private Date payDate;
    @Enumerated(EnumType.ORDINAL)
    private InvoiceStatus status;
    private float totalPrice;
}
