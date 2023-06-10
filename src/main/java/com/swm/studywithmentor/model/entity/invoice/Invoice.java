package com.swm.studywithmentor.model.entity.invoice;

import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.sql.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {
    @Column
    @Version
    private Long version;

    @Id
    @Type(type = "uuid-char")
    private UUID invoiceId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "invoice_id")
    private Enrollment enrollment;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    private Date payDate;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    private float totalPrice;
}
