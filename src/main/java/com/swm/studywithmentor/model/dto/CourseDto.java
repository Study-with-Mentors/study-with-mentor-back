package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.entity.course.CourseIntendedLearner;
import com.swm.studywithmentor.model.entity.course.CourseLevel;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto extends BaseDto {
    private String shortName;
    private String fullName;
    private String description;
    private String learningOutcome;
    private CourseStatus status;
    private CourseLevel courseLevel;
    private CourseIntendedLearner intendedLearner;
    private FieldDto field;
    private UserDto mentor;
    private ImageDto image;
}
