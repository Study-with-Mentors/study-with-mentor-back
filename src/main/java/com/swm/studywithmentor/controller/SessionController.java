package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.ActivityDto;
import com.swm.studywithmentor.model.dto.SessionDto;
import com.swm.studywithmentor.model.dto.create.SessionCreateDto;
import com.swm.studywithmentor.model.dto.update.SessionUpdateDto;
import com.swm.studywithmentor.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSession(@PathVariable UUID id) {
        SessionDto sessionDto = sessionService.getSession(id);
        return new ResponseEntity<>(sessionDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDto>> getActivitiesFromSession(@PathVariable(name = "id") UUID sessionId) {
        List<ActivityDto> dtos = sessionService.getActivitiesFromSession(sessionId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@RequestBody SessionCreateDto dto) {
        SessionDto resultDto = sessionService.createSession(dto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionDto> updateSession(@RequestBody SessionUpdateDto dto, @PathVariable UUID id) {
        dto.setId(id);
        SessionDto resultDto = sessionService.updateSession(dto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable UUID id) {
        sessionService.deleteSession(id);
    }
}
