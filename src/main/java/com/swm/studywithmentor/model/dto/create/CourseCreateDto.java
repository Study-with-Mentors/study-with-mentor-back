package com.swm.studywithmentor.model.dto.create;

import com.swm.studywithmentor.model.entity.course.CourseIntendedLearner;
import com.swm.studywithmentor.model.entity.course.CourseLevel;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseCreateDto {
    private String shortName;
    private String fullName;
    private String description;
    private String learningOutcome;
    private CourseStatus status;
    private CourseLevel courseLevel;
    private CourseIntendedLearner intendedLearner;
    private UUID fieldId;
}
