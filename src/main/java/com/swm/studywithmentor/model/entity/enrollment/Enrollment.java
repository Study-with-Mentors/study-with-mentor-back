package com.swm.studywithmentor.model.entity.enrollment;

import com.swm.studywithmentor.model.entity.Classes;
import com.swm.studywithmentor.model.entity.invoice.Invoice;
import com.swm.studywithmentor.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @Column
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classes;
    private Date enrollmentDate;
    @Enumerated(EnumType.ORDINAL)
    private EnrollmentStatus status;
    @OneToOne(mappedBy = "enrollment")
    @PrimaryKeyJoinColumn
    private Invoice invoice;
}
