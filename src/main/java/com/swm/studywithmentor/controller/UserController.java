package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.MentorDto;
import com.swm.studywithmentor.model.dto.StudentDto;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.dto.UserProfileDto;
import com.swm.studywithmentor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile() {
        UserProfileDto dto = userService.getCurrentUserProfile();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(@Valid @RequestBody UserDto dto) {
        UserDto resultDto = userService.updateProfile(dto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/profile/student")
    public ResponseEntity<StudentDto> updateStudentProfile(@Valid @RequestBody StudentDto studentDto) {
        StudentDto dto = userService.updateStudentProfile(studentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/profile/mentor")
    public ResponseEntity<MentorDto> updateMentorProfile(@Valid @RequestBody MentorDto mentorDto) {
        MentorDto dto = userService.updateMentorProfile(mentorDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
