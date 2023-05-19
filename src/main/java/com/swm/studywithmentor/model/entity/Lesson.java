package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.session.Session;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Column
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;
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
