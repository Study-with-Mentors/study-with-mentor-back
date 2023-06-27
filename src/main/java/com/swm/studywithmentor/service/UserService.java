package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.MentorDto;
import com.swm.studywithmentor.model.dto.SignupDto;
import com.swm.studywithmentor.model.dto.StudentDto;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.dto.UserProfileDto;
import com.swm.studywithmentor.model.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getCurrentUser();
    UserProfileDto getCurrentUserProfile();
    UserDto updateProfile(UserDto userDto);
    StudentDto updateStudentProfile(StudentDto studentDto);
    MentorDto updateMentorProfile(MentorDto mentorDto);
    UserDto addUser(SignupDto signupDto);
    void verifyActivationToken(String token);
    void resendActivationToken(String email);
}
