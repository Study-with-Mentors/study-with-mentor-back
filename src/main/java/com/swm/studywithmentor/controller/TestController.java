package com.swm.studywithmentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping
    public List<String> test() {
        return Stream.of("admin", "student1", "student2", "mentor1", "mentor2", "student3", "student4", "student5", "mentor3", "mentor4")
                .map(passwordEncoder::encode)
                .toList();
    }
}
