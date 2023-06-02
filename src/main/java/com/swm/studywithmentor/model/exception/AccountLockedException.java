package com.swm.studywithmentor.model.exception;

import com.swm.studywithmentor.model.entity.user.User;
import org.springframework.http.HttpStatus;

public class AccountLockedException extends ApplicationException {
    public static final String ERROR_CODE = ExceptionErrorCodeConstants.ACCOUNT_LOCKED;
    public static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public AccountLockedException(User user) {
        super(ERROR_CODE, HTTP_STATUS, "Account is locked. Id: " + user.getId());
        super.setPayload(user.getEmail());
    }
}
