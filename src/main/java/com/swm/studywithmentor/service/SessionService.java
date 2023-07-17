package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.ActivityDto;
import com.swm.studywithmentor.model.dto.SessionDto;
import com.swm.studywithmentor.model.dto.create.SessionCreateDto;
import com.swm.studywithmentor.model.dto.update.SessionUpdateDto;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    SessionDto getSession(UUID id);
    List<ActivityDto> getActivitiesFromSession(UUID sessionId);
    SessionDto updateSession(SessionUpdateDto sessionDto);
    SessionDto createSession(SessionCreateDto sessionDto);
    void deleteSession(UUID id);
}
