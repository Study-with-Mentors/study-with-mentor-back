package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.search.LessonTimeRangeDto;
import com.swm.studywithmentor.model.entity.Lesson;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ForbiddenException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.LessonRepository;
import com.swm.studywithmentor.service.LessonService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final ApplicationMapper mapper;
    private final UserService userService;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, ApplicationMapper mapper, UserService userService) {
        this.lessonRepository = lessonRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public LessonDto getLesson(UUID id) {
        Lesson lesson = findById(id);
        return mapper.toDto(lesson);
    }

    @Override
    public List<LessonDto> getLessonByTimeRange(LessonTimeRangeDto dto) {
        User user = userService.getCurrentUser();
        List<Lesson> lessons = new ArrayList<>();
        if (dto.getRole().equals("MENTOR")) {
            lessons = lessonRepository.findLessonInTimeRange(dto.getLowerTime(), dto.getUpperTime(), user.getId());
        } else if (dto.getRole().equals("STUDENT")) {
            lessons = lessonRepository.findLessonBetweenTimeAsStudent(dto.getLowerTime(), dto.getUpperTime(), user.getId());
        }
        return lessons.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public LessonDto updateLesson(LessonDto lessonDto) {
        User user = userService.getCurrentUser();
        Lesson lesson = findById(lessonDto.getId());
        if (!user.getId().equals(lesson.getClazz().getCourse().getMentor().getId())) {
            throw new ForbiddenException(Lesson.class, ActionConflict.UPDATE, "User does not own the course", user.getId());
        }
        mapper.toEntity(lessonDto, lesson);
        lessonRepository.save(lesson);
        return null;
    }

    private Lesson findById(UUID id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Lesson.class, id));
    }
}
