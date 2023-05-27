package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.course.CourseLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto extends BaseDto {
    private String shortName;
    private String fullName;
    private String description;
    private String learningOutcome;
    private CourseLevel courseLevel;
    private String intendedLearner;
    private FieldDto field;
    private UserDto mentor;
}
