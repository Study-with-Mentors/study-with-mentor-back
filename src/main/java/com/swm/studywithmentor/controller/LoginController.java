package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.LoginDto;
import com.swm.studywithmentor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public String login(@RequestBody LoginDto credential) {
        return loginService.authenticate(credential.getEmail(), credential.getPassword());
    }

    @PostMapping("/google")
    public String authenticateGoogle(@RequestBody String tokenId) {
        return loginService.authenticateGoogle(tokenId);
    }
}
