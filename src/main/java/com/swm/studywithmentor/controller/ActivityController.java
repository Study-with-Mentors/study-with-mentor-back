package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.ActivityDto;
import com.swm.studywithmentor.model.dto.create.ActivityCreateDtoAlone;
import com.swm.studywithmentor.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> getActivity(@PathVariable UUID id) {
        ActivityDto dto = activityService.getActivity(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ActivityDto> createActivity(@Valid @RequestBody ActivityCreateDtoAlone activityDto) {
        ActivityDto dto = activityService.createActivity(activityDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable UUID id, @Valid @RequestBody ActivityDto dto) {
        dto.setId(id);
        ActivityDto resultDto = activityService.updateActivity(dto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable UUID id) {
        activityService.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
