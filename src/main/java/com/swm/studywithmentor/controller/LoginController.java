package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public String login(@RequestBody Map<String, String> credential) {
        return loginService.authenticate(credential.get("email"), credential.get("password"));
    }

    @PostMapping("/google")
    public String authenticateGoogle(@RequestBody String tokenId) {
        return loginService.authenticateGoogle(tokenId);
    }
}
