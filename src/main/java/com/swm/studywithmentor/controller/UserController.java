package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.batch.NotificationScheduler;
import com.swm.studywithmentor.model.dto.MentorDto;
import com.swm.studywithmentor.model.dto.PageResult;
import com.swm.studywithmentor.model.dto.StudentDto;
import com.swm.studywithmentor.model.dto.UserDetailsDto;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.dto.UserProfileDto;
import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.dto.search.UserSearchDto;
import com.swm.studywithmentor.model.dto.update.NotificationTokenUpdateDto;
import com.swm.studywithmentor.service.ImageService;
import com.swm.studywithmentor.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final NotificationScheduler notificationSchedulerTest;

    @Autowired
    public UserController(UserService userService, ImageService imageService, NotificationScheduler notificationSchedulerTest) {
        this.userService = userService;
        this.imageService = imageService;
        this.notificationSchedulerTest = notificationSchedulerTest;
    }

    @GetMapping("/users")
    public PageResult<UserDetailsDto> getAllUsers(UserSearchDto searchDto) {
        return userService.getAllUsers(searchDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile() {
        UserProfileDto dto = userService.getCurrentUserProfile();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/me/image")
    public ResponseEntity<ImageDto> getProfileImage() {
        ImageDto dto = imageService.getProfileImage();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateProfile(@Valid @RequestBody UserDto dto) {
        UserDto resultDto = userService.updateProfile(dto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/me/image")
    public ResponseEntity<ImageDto> updateProfileImage(@Valid @RequestBody ImageDto imageDto) {
        ImageDto resultDto = imageService.updateProfileImage(imageDto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/me/student")
    public ResponseEntity<StudentDto> updateStudentProfile(@Valid @RequestBody StudentDto studentDto) {
        StudentDto dto = userService.updateStudentProfile(studentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/me/mentor")
    public ResponseEntity<MentorDto> updateMentorProfile(@Valid @RequestBody MentorDto mentorDto) {
        MentorDto dto = userService.updateMentorProfile(mentorDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("notification/token")
    public void updateNotificationToken(@RequestBody NotificationTokenUpdateDto token) {
        userService.updateNotificationToken(token.getToken());
    }

    @DeleteMapping("notification/token")
    public void deleteNotificationToken() {
        userService.deleteNotificationToken();
    }

    @Hidden
    @PostMapping("notification/testing/testing/testing")
    public void testNotification() {
        notificationSchedulerTest.sendNotifications();
    }
}
