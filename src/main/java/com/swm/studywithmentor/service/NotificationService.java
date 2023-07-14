package com.swm.studywithmentor.service;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface NotificationService {
    // send lesson notification
    void sendNotification(String token, String title, String body) throws FirebaseMessagingException;
}
