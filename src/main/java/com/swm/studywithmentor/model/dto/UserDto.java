package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.entity.user.Gender;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private ImageDto profileImage;
    private Gender gender;
}
