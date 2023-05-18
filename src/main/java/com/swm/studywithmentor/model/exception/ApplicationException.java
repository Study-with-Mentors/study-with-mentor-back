package com.swm.studywithmentor.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    private String errorCode;
    private Object payload;

    public ApplicationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
