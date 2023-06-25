package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.entity.user.Education;
import com.swm.studywithmentor.model.entity.user.Gender;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserProfileDto extends BaseDto {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private ImageDto profileImage;
    private Gender gender;
    private PrivateMentorDto mentor = new PrivateMentorDto();
    private PrivateStudentDto student = new PrivateStudentDto();
}

@Getter
@Setter
class PrivateMentorDto {
    private String bio;
    private Education degree;
    private FieldDto field;
}

@Getter
@Setter
class PrivateStudentDto {
    private int year;
    private String bio;
    private String experience;
    private Education education;
}
