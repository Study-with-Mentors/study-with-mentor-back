package com.swm.studywithmentor.model.exception;

import org.springframework.http.HttpStatus;

public class PasswordNotSetException extends ApplicationException {
    public PasswordNotSetException(String email) {
        super(ExceptionErrorCodeConstants.PASSWORD_NOT_SET,
                HttpStatus.UNAUTHORIZED,
                "Password is not set for the email: " + email);
        this.setPayload(email);
    }
}
