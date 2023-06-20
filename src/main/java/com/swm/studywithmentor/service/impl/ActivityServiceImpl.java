package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.ActivityDto;
import com.swm.studywithmentor.model.dto.create.ActivityCreateDtoAlone;
import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.ForbiddenException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.ActivityRepository;
import com.swm.studywithmentor.repository.SessionRepository;
import com.swm.studywithmentor.service.ActivityService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import com.swm.studywithmentor.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final SessionRepository sessionRepository;
    private final ApplicationMapper mapper;
    private final UserService userService;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, SessionRepository sessionRepository, ApplicationMapper mapper, UserService userService) {
        this.activityRepository = activityRepository;
        this.sessionRepository = sessionRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    private Activity findById(UUID id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Activity.class, id));
    }

    @Override
    public ActivityDto getActivity(UUID id) {
        Activity activity = findById(id);
        // find course
        Course course = activity.getSession().getCourse();
        if (course.getStatus() == CourseStatus.DRAFTING) {
            User user = userService.getCurrentUser();
            // For course status DRAFTING, if the user does not own the course
            // It will throw not found exception
            if (!user.getId().equals(course.getMentor().getId())) {
                throw new NotFoundException(Activity.class, id);
            }
        }
        return mapper.toDto(activity);
    }

    @Override
    public ActivityDto createActivity(ActivityCreateDtoAlone dto) {
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new NotFoundException(Session.class, dto.getSessionId()));
        Course course = session.getCourse();
        if (!Utils.isCourseOpenForEdit(course)) {
            throw new ConflictException(Activity.class, ActionConflict.CREATE, "Course is not open for edit", course.getId());
        }
        if (!isUserHasPermissionToEdit(course)) {
            throw new ForbiddenException(Activity.class, ActionConflict.CREATE, "User does not own the course", userService.getCurrentUser().getId());
        }

        Activity activity = mapper.toEntity(dto);
        activity.setId(null);
        activity.setSession(session);
        session.getActivities().add(activity);
        activity = activityRepository.save(activity);
        return mapper.toDto(activity);
    }

    @Override
    public ActivityDto updateActivity(ActivityDto dto) {
        Activity activity = findById(dto.getId());
        Course course = activity.getSession().getCourse();
        if (!Utils.isCourseOpenForEdit(course)) {
            throw new ConflictException(Activity.class, ActionConflict.UPDATE, "Course is not open for edit", course.getId());
        }
        if (!isUserHasPermissionToEdit(course)) {
            throw new ForbiddenException(Activity.class, ActionConflict.UPDATE, "User does not own the course", userService.getCurrentUser().getId());
        }
        mapper.toDto(dto, activity);
        activity = activityRepository.save(activity);

        return mapper.toDto(activity);
    }

    @Override
    public void deleteActivity(UUID id) {
        Activity activity = findById(id);
        Course course = activity.getSession().getCourse();
        if (!Utils.isCourseOpenForEdit(course)) {
            throw new ConflictException(Activity.class, ActionConflict.DELETE, "Course is not open for edit", course.getId());
        }
        if (!isUserHasPermissionToEdit(course)) {
            throw new ForbiddenException(Activity.class, ActionConflict.DELETE, "User does not own the course", userService.getCurrentUser().getId());
        }
        activityRepository.delete(activity);
    }

    private boolean isUserHasPermissionToEdit(Course course) {
        User user = userService.getCurrentUser();
        return user.getId().equals(course.getMentor().getId());
    }
}
