package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.entity.user.Gender;
import com.swm.studywithmentor.model.entity.user.Role;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserDetailsDto extends BaseDto {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private ImageDto profileImage;
    private Gender gender;
    private Role role;
    private boolean enabled;
}
