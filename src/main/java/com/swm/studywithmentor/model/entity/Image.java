package com.swm.studywithmentor.model.entity;

import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "image_id"))
public class Image extends BaseEntity {
    @Column
    @Version
    private Long version;

    private String url;
    @OneToOne(mappedBy = "image")
    private Course course;

    @OneToOne(mappedBy = "profileImage")
    private User user;
}
