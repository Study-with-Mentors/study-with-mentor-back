package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.session.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.sql.Timestamp;

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
    private Timestamp startTime;
    private Timestamp endTime;
    private String location;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Clazz clazz;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

}
