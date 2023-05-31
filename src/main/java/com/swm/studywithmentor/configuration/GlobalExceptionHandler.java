package com.swm.studywithmentor.configuration;

import com.swm.studywithmentor.model.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ResponseMessage> handleException(ApplicationException ex, HttpServletRequest servlet) {
        ResponseMessage responseMessage = new ResponseMessage(ex.getMessage(), ex.getStatus(), servlet.getContextPath());
        return new ResponseEntity<>(responseMessage, ex.getStatus());
    }
}

@Getter
@Setter
@AllArgsConstructor
class ResponseMessage {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String path;

    public ResponseMessage(String message, HttpStatus status, String path) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
