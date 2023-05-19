package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.session.Session;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Column
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;
    private String description;
    private String title;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
}
