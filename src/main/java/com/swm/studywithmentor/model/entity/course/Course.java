package com.swm.studywithmentor.model.entity.course;

import com.swm.studywithmentor.model.entity.BaseEntity;
import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.Image;
import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.model.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private CourseIntendedLearner intendedLearner;
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User mentor;
    @OneToMany(mappedBy = "course")
    private List<Clazz> clazzes;
    @OneToMany(mappedBy = "course")
    private List<Session> sessions;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
