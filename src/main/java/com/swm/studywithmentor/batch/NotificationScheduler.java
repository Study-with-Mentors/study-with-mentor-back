package com.swm.studywithmentor.batch;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.swm.studywithmentor.model.dto.LessonDto;
import com.swm.studywithmentor.model.dto.search.LessonTimeRangeDto;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.LessonService;
import com.swm.studywithmentor.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class NotificationScheduler {
    private final NotificationService notificationService;
    private final LessonService lessonService;
    private final UserRepository userRepository;

    @Autowired
    public NotificationScheduler(NotificationService notificationService, LessonService lessonService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.lessonService = lessonService;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedDelay = 300000)
    @Async
    public void sendNotifications() {
        // get user that have registration token
        log.info("Sending notification");
        List<User> notifiedUsers = userRepository.findAllByNotificationTokenNotNull();
        var timeRange = new LessonTimeRangeDto();
        Timestamp lowerTime = Timestamp.from(Instant.now());
        Timestamp upperTime = Timestamp.from(Instant.now().plus(7, ChronoUnit.DAYS));
        timeRange.setLowerTime(lowerTime);
        timeRange.setUpperTime(upperTime);
        List<String> failedTokens = new ArrayList<>();
        for (User user : notifiedUsers) {
            // user upcoming lessons
            List<LessonDto> results = lessonService.getLessonByTimeRange(user, timeRange);
            try {
                notificationService.sendNotification(user.getNotificationToken(), "Upcoming lessons", MessageFormat.format("You have {0} upcoming lessons in this week", results.size()));
            } catch (FirebaseMessagingException ex) {
                log.warn("Sending notif failed for token {}", user.getNotificationToken());
                ex.printStackTrace();
                failedTokens.add(user.getNotificationToken());
            }
        }
        if (!failedTokens.isEmpty()) {
            log.info("Failed token {}", String.join(",", failedTokens));
        }
    }
}