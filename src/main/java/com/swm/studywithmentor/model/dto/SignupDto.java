package com.swm.studywithmentor.model.dto;

import com.swm.studywithmentor.model.entity.user.Gender;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
public class SignupDto extends BaseDto {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotBlank
    private String firstName;
    private String lastName;
    private Date birthdate;
    @NotNull
    private Gender gender;
}
