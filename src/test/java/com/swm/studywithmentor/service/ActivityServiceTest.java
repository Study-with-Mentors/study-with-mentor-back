package com.swm.studywithmentor.service;

import com.swm.studywithmentor.BaseServiceTest;
import com.swm.studywithmentor.model.dto.ActivityDto;
import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

class ActivityServiceTest extends BaseServiceTest {
    @Autowired
    private ActivityService activityService;
    @MockBean
    private UserService userService;

    @Test
    void testGetActivityAssertTrue() {
        UUID activityId = UUID.randomUUID();
        UUID mentorId = UUID.randomUUID();
        User mentor = new User();
        mentor.setId(mentorId);

        Activity activity = Activity.builder()
                .session(Session.builder()
                        .course(Course.builder()
                                .mentor(mentor)
                                .status(CourseStatus.DRAFTING)
                                .build())
                        .build())
                .build();
        activity.setId(activityId);

        given(activityRepository.findById(activityId))
                .willReturn(Optional.of(activity));
        given(userService.getCurrentUser()).willReturn(mentor);

        ActivityDto dto = activityService.getActivity(activityId);
        assertEquals(activityId, dto.getId());
    }

    @Test
    void testGetActivityAssertThrows() {
        UUID mentorId = UUID.randomUUID();
        User mentor = new User();
        mentor.setId(mentorId);
        User user = new User();
        user.setId(UUID.randomUUID());

        Activity activity = Activity.builder()
                .session(Session.builder()
                        .course(Course.builder()
                                .mentor(mentor)
                                .status(CourseStatus.DRAFTING)
                                .build())
                        .build())
                .build();

        given(activityRepository.findById(any()))
                .willReturn(Optional.ofNullable(activity));
        given(userService.getCurrentUser()).willReturn(user);

        assertThrows(NotFoundException.class, () -> activityService.getActivity(UUID.randomUUID()));
    }
}