package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.ActivityDto;
import com.swm.studywithmentor.model.dto.create.ActivityCreateDtoAlone;

import java.util.UUID;

public interface ActivityService {
    ActivityDto getActivity(UUID id);
    ActivityDto createActivity(ActivityCreateDtoAlone dto);
    ActivityDto updateActivity(ActivityDto dto);
    void deleteActivity(UUID id);
}
