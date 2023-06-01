package com.swm.studywithmentor.model.exception;

/**
 * This exception is thrown when the email address has been verified via Google OAuth but is not in the database.
 */
public class ValidEmailNotInDatabaseException extends ApplicationException {
    public static final String ERROR_CODE = "VALID_EMAIL_NOT_IN_DATABASE";
    public static final int HTTP_STATUS_CODE = 302;

    public ValidEmailNotInDatabaseException(String email, Throwable cause) {
        super(ERROR_CODE,
            HTTP_STATUS_CODE,
            "The email address has been verified via Google OAuth but is not in the database. Email: " + email,
            cause);
    }
}
