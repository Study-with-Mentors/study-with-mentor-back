package com.swm.studywithmentor.model.entity.session;

import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.BaseEntity;
import com.swm.studywithmentor.model.entity.Lesson;
import javax.persistence.*;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "session_id"))
public class Session extends BaseEntity {
    @Column
    @Version
    private Long version;

    private long sessionNum;
    private String sessionName;
    @Enumerated(EnumType.STRING)
    private SessionType type;
    private String description;
    private String resource;
    @OneToMany(mappedBy = "session")
    private Set<Activity> activities;
    @OneToMany(mappedBy = "session")
    private Set<Lesson> lessons;

}
