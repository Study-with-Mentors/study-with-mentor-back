package com.swm.studywithmentor.model.exception;

import org.springframework.http.HttpStatus;

public class UserNotVerifiedExceptions  extends ApplicationException {
    public static final String ERROR_CODE = ExceptionErrorCodeConstants.USER_NOT_VERIFIED;
    public static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public UserNotVerifiedExceptions(String email, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, "User is not verified: " + email, cause);
        this.setPayload(email);
    }
}
