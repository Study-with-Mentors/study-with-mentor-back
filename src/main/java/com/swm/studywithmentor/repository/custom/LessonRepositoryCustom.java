package com.swm.studywithmentor.repository.custom;

import com.swm.studywithmentor.model.entity.Lesson;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface LessonRepositoryCustom {
    List<Lesson> findLessonBetweenTime(Timestamp startTime, Timestamp endTime, UUID mentorId);
}
