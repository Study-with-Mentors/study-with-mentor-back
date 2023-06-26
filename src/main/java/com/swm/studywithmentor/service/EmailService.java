package com.swm.studywithmentor.service;

public interface EmailService {
    void sendEmailVerification(String email, String name);
}
