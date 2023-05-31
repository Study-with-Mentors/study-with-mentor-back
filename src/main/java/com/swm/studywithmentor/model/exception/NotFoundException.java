package com.swm.studywithmentor.model.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class NotFoundException extends ApplicationException {
    public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(Class<?> clazz, UUID id) {
        super(ExceptionErrorCodeConstants.NOT_FOUND,
                HTTP_STATUS,
                String.format("%s not found. Id: %s",
                        clazz.getSimpleName(),
                        id.toString()));
        this.setPayload(clazz.getSimpleName() + "#" + id);
    }

    public NotFoundException(Class<?> clazz, UUID id, Throwable cause) {
        super(ExceptionErrorCodeConstants.NOT_FOUND,
                HTTP_STATUS,
                String.format("%s not found. Id: %s",
                        clazz.getSimpleName(),
                        id.toString()),
                cause);
        this.setPayload(clazz.getSimpleName() + "#" + id);
    }
}
