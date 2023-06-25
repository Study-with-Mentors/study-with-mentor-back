package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.ActivityDto;
import com.swm.studywithmentor.model.dto.SessionDto;
import com.swm.studywithmentor.model.dto.create.SessionCreateDto;
import com.swm.studywithmentor.model.dto.update.SessionUpdateDto;
import com.swm.studywithmentor.model.entity.Activity;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.session.Session;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.EntityOptimisticLockingException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.SessionRepository;
import com.swm.studywithmentor.service.SessionService;
import com.swm.studywithmentor.util.ApplicationMapper;
import com.swm.studywithmentor.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
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
    public List<ActivityDto> getActivitiesFromSession(UUID sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException(Session.class, sessionId));
        return session.getActivities().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<SessionDto> getSessionsByCourseId(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(Course.class, courseId));
        return course.getSessions().stream()
                .sorted(Comparator.comparing(Session::getSessionNum))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public SessionDto updateSession(SessionUpdateDto sessionDto) {
        Session session = sessionRepository.findById(sessionDto.getId())
                .orElseThrow(() -> new NotFoundException(Session.class, sessionDto.getId()));

        if (!Objects.equals(session.getVersion(), sessionDto.getVersion())) {
            throw new EntityOptimisticLockingException(session, session.getId());
        }
        Course course = session.getCourse();
        if (!Utils.isCourseOpenForEdit(course)) {
            throw new ConflictException(Session.class, ActionConflict.UPDATE, "Course is unable to modify", course.getId());
        }
        // update session num of all session in the course
        long oldSessionNum = session.getSessionNum();
        long newSessionNum = sessionDto.getSessionNum();
        mapper.toEntity(sessionDto, session);
        // Some shitty things about hibernate managing state make me have to set session num manually
        session.setSessionNum(oldSessionNum);
        if (oldSessionNum != newSessionNum) {
            if (oldSessionNum < newSessionNum) {
                sessionRepository.decreaseSessionNum(course, oldSessionNum, newSessionNum);
                long maxNum = sessionRepository.findMaxSessionNum(course);
                if (newSessionNum > maxNum) {
                    newSessionNum = maxNum;
                }
            } else {
                // case for oldSessionNum > newSessionNum
                sessionRepository.increaseSessionNum(course, newSessionNum, oldSessionNum);
                if (newSessionNum < 1) {
                    newSessionNum = 1;
                }
            }
            session.setSessionNum(newSessionNum);
        }

        session = sessionRepository.save(session);

        return mapper.toDto(session);
    }

    @Override
    public SessionDto createSession(SessionCreateDto sessionDto) {
        Course course = courseRepository.findById(sessionDto.getCourseId())
                .orElseThrow(() -> new NotFoundException(Course.class, sessionDto.getCourseId()));
        if (!Utils.isCourseOpenForEdit(course)) {
            throw new ConflictException(Session.class, ActionConflict.CREATE, "Course is unable to modify", course.getId());
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

        if (session.getSessionNum() == 0) {
            long nextSessionNum = sessionRepository.findMaxSessionNum(course);
            session.setSessionNum(nextSessionNum+1);
        } else {
            sessionRepository.increaseSessionNum(course, session.getSessionNum());
        }
        session = sessionRepository.save(session);

        return mapper.toDto(session);
    }

    @Override
    public void deleteSession(UUID id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Session.class, id));
        Course course = session.getCourse();
        if (!Utils.isCourseOpenForEdit(course)) {
            throw new ConflictException(Session.class, ActionConflict.DELETE, "Course is not open for editing. Id: " + course.getId(), course.getId());
        }
        // there is no need to check number of clazz in a course
        // because the check for status ensure that there is no clazz for course

        // update session number
        sessionRepository.decreaseSessionNum(course, session.getSessionNum());
        sessionRepository.delete(session);
    }
}
