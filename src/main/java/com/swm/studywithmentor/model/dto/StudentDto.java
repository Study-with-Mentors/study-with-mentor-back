package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.user.Education;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto extends BaseDto {
    private int year;
    private String bio;
    private String experience;
    private Education education;
}
