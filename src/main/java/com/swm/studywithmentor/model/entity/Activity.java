package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.session.Session;
import javax.persistence.*;

import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "activity_id"))
public class Activity extends BaseEntity {
    @Column
    @Version
    private Long version;

    private String description;
    private String title;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
}
