package com.swm.studywithmentor.model.entity.session;

import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.BaseEntity;
import com.swm.studywithmentor.model.entity.Lesson;
import com.swm.studywithmentor.model.entity.course.Course;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Activity> activities;
    @OneToMany(mappedBy = "session")
    private List<Lesson> lessons;
}
