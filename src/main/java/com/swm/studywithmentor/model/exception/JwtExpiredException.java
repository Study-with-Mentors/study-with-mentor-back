package com.swm.studywithmentor.model.exception;

public class JwtExpiredException extends ApplicationException {
    public static final String ERROR_CODE = "EXPIRED_JWT";
    // TODO: Need to change HTTP status code
    public static final int HTTP_STATUS_CODE = 400;

    public JwtExpiredException(String jwt, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS_CODE, "The JWT has expried. JWT: " + jwt, cause);
    }
}
