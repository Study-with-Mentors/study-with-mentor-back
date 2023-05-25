package com.swm.studywithmentor.model.entity.user;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Column
    @Version
    private Long version;

    @Id
    private UUID studentId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;
    private int year;
    private String bio;
    private String experience;
    @Enumerated(EnumType.STRING)
    private Education Education;
}
