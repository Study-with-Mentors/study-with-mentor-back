package com.swm.studywithmentor.model.exception;

public class GoogleIdTokenVerificationFailedException extends ApplicationException {
    public static final String ERROR_CODE = "GOOGLE_ID_TOKEN_VERIFICATION_FAILED";
    public static final int HTTP_STATUS_CODE = 401;

    public GoogleIdTokenVerificationFailedException(String idToken) {
        super(ERROR_CODE, HTTP_STATUS_CODE, "Cannot verify the ID token. ID token: " + idToken);
    }

    public GoogleIdTokenVerificationFailedException(String idToken, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS_CODE, "Cannot verify the ID token. ID token: " + idToken, cause);
    }
}
