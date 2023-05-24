package com.swm.studywithmentor.model.entity.enrollment;

import com.swm.studywithmentor.model.entity.BaseEntity;
import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.invoice.Invoice;
import com.swm.studywithmentor.model.entity.user.User;
import javax.persistence.*;

import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "enrollment_id"))
public class Enrollment extends BaseEntity {
    @Column
    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Clazz clazz;
    private Date enrollmentDate;
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
    @OneToOne(mappedBy = "enrollment")
    @PrimaryKeyJoinColumn
    private Invoice invoice;
}
