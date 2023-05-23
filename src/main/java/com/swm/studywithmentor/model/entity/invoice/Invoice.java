package com.swm.studywithmentor.model.entity.invoice;

import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Column
    @Version
    private Long version;

    @Id
    private UUID invoiceId;
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
