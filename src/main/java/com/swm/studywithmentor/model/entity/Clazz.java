package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import javax.persistence.*;

import lombok.*;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "class")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "class_id"))
public class Clazz extends BaseEntity{
    @Column
    @Version
    private Long version;

    private Date startDate;
    private Date endDate;
    private Date enrollmentEndDate;
    private float price;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "clazz")
    private Set<Lesson> lessons;
    @OneToMany(mappedBy = "clazz")
    private Set<Enrollment> enrollments;
}
