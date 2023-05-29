package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.SessionDto;
import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.SessionRepository;
import com.swm.studywithmentor.service.SessionService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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
        return mapper.toDto(session);
    }

    @Override
    public List<SessionDto> getSessions() {
        // TODO: implement get session by course id
        throw new NotImplementedException();
    }

    @Override
    public List<SessionDto> getSessionsByCourseId(UUID courseId) {
        // TODO: test this method
        List<Session> sessions = sessionRepository.findByCourseId(courseId);
        return sessions.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SessionDto updateSession(SessionDto sessionDto) {
        // not allow changing session's course
        sessionDto.setCourse(null);
        Session session = sessionRepository.findById(sessionDto.getId())
                .orElseThrow(() -> new NotFoundException(Session.class, sessionDto.getId()));
        // TODO: fix dto might override activities
        mapper.toEntity(sessionDto, session);
        // TODO: update activities as well

        session = sessionRepository.save(session);

        return mapper.toDto(session);
    }

    @Override
    public SessionDto createSession(SessionDto sessionDto) {
        // TODO: test this
        Course course = courseRepository.findById(sessionDto.getCourse().getId())
                .orElseThrow(() -> new NotFoundException(Course.class, sessionDto.getCourse().getId()));
        if (course.getSessions().stream().anyMatch(s -> s.getSessionNum() == sessionDto.getSessionNum())) {
            throw new ConflictException(Session.class, ActionConflict.CREATE, "Duplicate session number", sessionDto.getSessionNum());
        }
        Session session = mapper.toEntity(sessionDto);
        Set<Activity> activities = sessionDto.getActivities().stream()
                .map(mapper::toEntity)
                .collect(Collectors.toSet());

        // mapping
        session.setActivities(activities);
        session.setCourse(course);
        course.getSessions().add(session);

        session = sessionRepository.save(session);

        return mapper.toDto(session);
    }

    @Override
    public void deleteSession(UUID id) {
        // TODO: cascade delete all activity
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Session.class, id));
        // TODO: delete only when there is not clazz
        int numOfClazz = 0;
        if (numOfClazz != 0) {
            sessionRepository.delete(session);
        } else {
            throw new ConflictException(Session.class, ActionConflict.DELETE, "Cannot delete session after starting classes", id);
        }
    }
}
