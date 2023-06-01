package com.swm.studywithmentor.service;

public interface LoginService {
    String authenticate(String email, String password);

    String authenticateGoogle(String idTokenString);
}
