package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.SessionDto;
import com.swm.studywithmentor.model.dto.create.SessionCreateDto;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    SessionDto getSession(UUID id);
    List<SessionDto> getSessions();
    List<SessionDto> getSessionsByCourseId(UUID courseId);
    SessionDto updateSession(SessionDto sessionDto);
    SessionDto createSession(SessionCreateDto sessionDto);
    void deleteSession(UUID id);
}
