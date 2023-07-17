package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.MentorDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.SignupDto;
import com.swm.studywithmentor.model.dto.StudentDto;
import com.swm.studywithmentor.model.dto.UserDetailsDto;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.dto.UserProfileDto;
import com.swm.studywithmentor.model.dto.search.MentorSearchDto;
import com.swm.studywithmentor.model.dto.search.UserSearchDto;
import com.swm.studywithmentor.model.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    PageResult<UserDetailsDto> getAllUsers(UserSearchDto searchDto);
    User getCurrentUser();
    UserProfileDto getCurrentUserProfile();
    UserDto updateProfile(UserDto userDto);
    StudentDto updateStudentProfile(StudentDto studentDto);
    MentorDto updateMentorProfile(MentorDto mentorDto);
    UserDto addUser(SignupDto signupDto);
    void verifyActivationToken(String token);
    void resendActivationToken(String email);
    UserProfileDto getMentorProfile(UUID mentorId);
    PageResult<UserProfileDto> searchMentors(MentorSearchDto searchDto);
    void updateNotificationToken(String token);
    void deleteNotificationToken();
}
