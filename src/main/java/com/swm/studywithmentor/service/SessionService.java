package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.SessionDto;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    // TODO: implement session controller
    SessionDto getSession(UUID id);
    List<SessionDto> getSessions();
    List<SessionDto> getSessionsByCourseId(UUID courseId);
    SessionDto updateSession(SessionDto sessionDto);
    SessionDto createSession(SessionDto sessionDto);
    void deleteSession(UUID id);
}
