package com.swm.studywithmentor.model.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationException {
    public static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;
    private static final String messageFormat = "User not allow to %s %s. %s";

    public ForbiddenException(Class<?> clazz, ActionConflict action, String causeMessage, Object... args) {
        super(ExceptionErrorCodeConstants.FORBIDDEN,
                HTTP_STATUS,
                messageFormat.formatted(action.getName(), clazz.getSimpleName(), causeMessage));
        this.setPayload(args);
    }

    public ForbiddenException(Class<?> clazz, ActionConflict action, String causeMessage, Throwable cause, Object... args) {
        super(ExceptionErrorCodeConstants.FORBIDDEN,
                HTTP_STATUS,
                messageFormat.formatted(action.getName(), clazz.getSimpleName(), causeMessage),
                cause);
        this.setPayload(args);
    }
}
