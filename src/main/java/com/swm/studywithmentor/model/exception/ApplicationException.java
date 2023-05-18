package com.swm.studywithmentor.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    private final String errorCode;
    private Object payload;
    private final int httpStatusCode;

    public ApplicationException(String errorCode, int httpStatusCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    public ApplicationException(String errorCode, int httpStatusCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }
}
