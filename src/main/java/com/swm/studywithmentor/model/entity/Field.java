package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.user.Mentor;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "field_id"))
public class Field extends BaseEntity{
    @Column
    @Version
    private Long version;

    private String code;
    private String name;
    private String description;
    @OneToMany(mappedBy = "field")
    private List<Mentor> mentors;
    @OneToMany(mappedBy = "field")
    private List<Course> courses;
}
