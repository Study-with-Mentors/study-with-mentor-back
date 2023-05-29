package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.user.Gender;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String profileImage;
    private Gender gender;
}