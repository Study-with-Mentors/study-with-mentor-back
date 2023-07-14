package com.swm.studywithmentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudyWithMentorApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyWithMentorApplication.class, args);
    }

}
