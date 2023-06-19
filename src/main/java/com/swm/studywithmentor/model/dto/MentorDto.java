package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.user.Education;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorDto extends BaseDto {
    private String bio;
    private Education degree;
    private FieldDto field;
}
