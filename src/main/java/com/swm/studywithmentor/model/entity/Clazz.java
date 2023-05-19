package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Clazz {
    @Column
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;
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
