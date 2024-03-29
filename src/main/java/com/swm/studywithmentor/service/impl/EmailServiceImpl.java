package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.service.EmailService;
import com.swm.studywithmentor.util.EmailTemplate;
import com.swm.studywithmentor.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private static final String SENDER_NAME = "Study With Mentor";
    private final JwtTokenProvider tokenProvider;
    private final JavaMailSender sender;

    @Value("${app.email-sender}")
    private String from;

    @Value("${app.web-host}")
    private String webHost;

    @Autowired
    public EmailServiceImpl(JwtTokenProvider tokenProvider, JavaMailSender sender) {
        this.tokenProvider = tokenProvider;
        this.sender = sender;
    }

    @Override
    @Async
    public void sendEmailVerification(String email, String name) {
        try {
            String verifyUrl = webHost + "/signup/verify?token=" + tokenProvider.generateEmailVerificationToken(email);

            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(EmailTemplate.buildConfirmCodeEmail(verifyUrl), true);
            helper.setSubject("Confirm your email");
            helper.setTo(email);
            helper.setFrom(from, SENDER_NAME);
            sender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
