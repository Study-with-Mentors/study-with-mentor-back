package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.user.Mentor;
import javax.persistence.*;

import lombok.*;

import java.util.Set;
import java.util.UUID;

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

    private String name;
    private String description;
    @OneToMany(mappedBy = "field")
    private Set<Mentor> mentors;
    @OneToMany(mappedBy = "field")
    private Set<Course> courses;
}
