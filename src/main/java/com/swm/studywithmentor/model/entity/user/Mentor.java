package com.swm.studywithmentor.model.entity.user;

import com.swm.studywithmentor.model.entity.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.util.UUID;

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
    @Type(type = "uuid-char")
    private UUID mentorId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "mentor_id")
    private User user;
    private String bio;
    @Enumerated(EnumType.STRING)
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
