package com.swm.studywithmentor.model.entity.user;

import com.swm.studywithmentor.model.entity.Field;
import javax.persistence.*;
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
public class Mentor {
    @Column
    @Version
    private Long version;

    @Id
    private Long mentorId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "mentor_id")
    private User user;
    private String bio;
    private Education degree;
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    public void setDegree(Education degree) {
        if(degree.equals(Education.NONE) || degree.equals(Education.COLLEGE) || degree.equals(Education.HIGH_SCHOOL))
            throw new IllegalArgumentException("Mentor must have degree from bachelor or above");
        this.degree = degree;
    }
}
