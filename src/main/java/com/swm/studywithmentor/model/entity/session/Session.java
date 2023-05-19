package com.swm.studywithmentor.model.entity.session;

import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.Lesson;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Column
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;
    private long sessionNum;
    private String sessionName;
    @Enumerated(EnumType.ORDINAL)
    private SessionType type;
    private String description;
    private String resource;
    @OneToMany(mappedBy = "session")
    private Set<Activity> activities;
    @OneToMany(mappedBy = "session")
    private Set<Lesson> lessons;

}
