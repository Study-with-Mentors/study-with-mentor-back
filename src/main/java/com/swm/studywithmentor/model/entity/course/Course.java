package com.swm.studywithmentor.model.entity.course;

import com.swm.studywithmentor.model.entity.Classes;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Column
    @Version
    private Long version;

    @Id
    private Long courseId;
    private String shortName;
    private String fullName;
    private String description;
    private String learningOutcome;
    private String intendedLearner;
    @Enumerated(EnumType.ORDINAL)
    private CourseLevel courseLevel;
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "course")
    private Set<Classes> classes;
}
