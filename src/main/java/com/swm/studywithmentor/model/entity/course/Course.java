package com.swm.studywithmentor.model.entity.course;

import com.swm.studywithmentor.model.entity.BaseEntity;
import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "course_id"))
public class Course extends BaseEntity {
    @Column
    @Version
    private Long version;

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
    private Set<Clazz> clazzes;
}
