package com.swm.studywithmentor.model.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when the email address has been verified via Google OAuth but is not in the database.
 */
public class ValidEmailNotInDatabaseException extends ApplicationException {
    public static final String ERROR_CODE = ExceptionErrorCodeConstants.VALID_EMAIL_NOT_IN_DATABASE;
    public static final HttpStatus HTTP_STATUS = HttpStatus.FOUND;

    public ValidEmailNotInDatabaseException(String email, Throwable cause) {
        super(ERROR_CODE,
            HTTP_STATUS,
            "The email address has been verified via Google OAuth but is not in the database. Email: " + email,
            cause);
    }
}
