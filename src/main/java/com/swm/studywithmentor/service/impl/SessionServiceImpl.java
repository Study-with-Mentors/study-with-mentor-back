package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.SessionDto;
import com.swm.studywithmentor.model.dto.create.SessionCreateDto;
import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.EntityOptimisticLockingException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.SessionRepository;
import com.swm.studywithmentor.service.SessionService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
    private final ApplicationMapper mapper;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, CourseRepository courseRepository, ApplicationMapper mapper) {
        this.sessionRepository = sessionRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public SessionDto getSession(UUID id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Session.class, id));

        SessionDto dto = mapper.toDto(session);
        dto.setCourseId(session.getCourse().getId());
        return dto;
    }

    @Override
    public List<SessionDto> getSessions() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<SessionDto> getSessionsByCourseId(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(Course.class, courseId));
        return course.getSessions().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public SessionDto updateSession(SessionDto sessionDto) {
        // not allow changing session's course
        sessionDto.setCourseId(null);
        Session session = sessionRepository.findById(sessionDto.getId())
                .orElseThrow(() -> new NotFoundException(Session.class, sessionDto.getId()));

        if (!Objects.equals(session.getVersion(), sessionDto.getVersion())) {
            throw new EntityOptimisticLockingException(session, session.getId());
        }
        if (isCourseOpenForEdit(session.getCourse())) {
            throw new ConflictException(Session.class, ActionConflict.UPDATE, "Course is unable to modify", session.getCourse().getId());
        }
        mapper.toEntity(sessionDto, session);
        List<Activity> activities = sessionDto.getActivities().stream()
                .map(mapper::toEntity)
                .toList();

        // mapping
        session.setActivities(activities);
        for (Activity activity : session.getActivities()) {
            activity.setSession(session);
        }

        session = sessionRepository.save(session);

        return mapper.toDto(session);
    }

    @Override
    public SessionDto createSession(SessionCreateDto sessionDto) {
        Course course = courseRepository.findById(sessionDto.getCourseId())
                .orElseThrow(() -> new NotFoundException(Course.class, sessionDto.getCourseId()));
        if (isCourseOpenForEdit(course)) {
            throw new ConflictException(Session.class, ActionConflict.CREATE, "Course is unable to modify", course.getId());
        }
        if (course.getSessions().stream().anyMatch(s -> s.getSessionNum() == sessionDto.getSessionNum())) {
            throw new ConflictException(Session.class, ActionConflict.CREATE, "Duplicate session number", sessionDto.getSessionNum());
        }
        Session session = mapper.toEntity(sessionDto);
        List<Activity> activities = sessionDto.getActivities().stream()
                .map(mapper::toEntity)
                .toList();

        // mapping
        session.setActivities(activities);
        for (Activity activity : session.getActivities()) {
            activity.setSession(session);
        }
        session.setCourse(course);
        course.getSessions().add(session);

        session = sessionRepository.save(session);

        return mapper.toDto(session);
    }

    @Override
    public void deleteSession(UUID id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Session.class, id));
        Course course = session.getCourse();
        if (!isCourseOpenForEdit(course)) {
            throw new ConflictException(Session.class, ActionConflict.DELETE, "Course is not open for editing. Id: " + course.getId(), course.getId());
        }
        // FIXME: optimistic locking
        // there is no need to check number of clazz in a course
        // because the check for status ensure that there is no clazz for course
        sessionRepository.delete(session);
    }

    private boolean isCourseOpenForEdit(Course course) {
        return course.getStatus() == CourseStatus.OPEN || course.getStatus() == CourseStatus.DRAFTING;
    }
}
