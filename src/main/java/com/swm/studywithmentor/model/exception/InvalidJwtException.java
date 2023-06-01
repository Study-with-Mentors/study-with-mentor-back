package com.swm.studywithmentor.model.exception;

public class InvalidJwtException extends ApplicationException {
    public static final String ERROR_CODE = "INVALID_JWT";
    // TODO: Need to change HTTP status code
    public static final int HTTP_STATUS_CODE = 400;

    public InvalidJwtException(String jwt, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS_CODE, "The JWT is invalid. JWT: " + jwt, cause);
    }
}
