package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.session.Session;
import javax.persistence.*;

import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "lession_id"))
public class Lesson extends BaseEntity{
    @Column
    @Version
    private Long version;

    private long lessonNum;
    private Date startTime;
    private Date endTime;
    private String location;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Clazz clazz;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

}
