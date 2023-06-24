package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.search.LessonTimeRangeDto;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    LessonDto getLesson(UUID id);
    List<LessonDto> getLessonByTimeRange(LessonTimeRangeDto dto);
    LessonDto updateLesson(LessonDto lessonDto);
}
