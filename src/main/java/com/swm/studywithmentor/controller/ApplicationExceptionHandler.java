package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorMessage> applicationUnexpectedException(ApplicationException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatus())
            .body(new ErrorMessage(ex.getErrorCode(), ex.getMessage(), ex.getPayload()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleAllException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
            .body(new ErrorMessage("Unexpected error happens", ex.getMessage()));
    }
}

@Getter
@AllArgsConstructor
class ErrorMessage {
    private String errorCode;
    private String message;
    private Object payload;

    ErrorMessage(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
