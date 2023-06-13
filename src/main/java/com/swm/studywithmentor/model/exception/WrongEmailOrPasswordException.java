package com.swm.studywithmentor.model.exception;

import org.springframework.http.HttpStatus;

public class WrongEmailOrPasswordException extends ApplicationException {
    public WrongEmailOrPasswordException(String email, Throwable cause) {
        super(ExceptionErrorCodeConstants.WRONG_EMAIL_OR_PASSWORD,
                HttpStatus.UNAUTHORIZED,
                "Wrong email or password. Email: " + email,
                cause);
    }
}
