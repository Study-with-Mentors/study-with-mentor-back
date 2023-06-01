package com.swm.studywithmentor.model.exception;

import com.swm.studywithmentor.model.entity.user.User;

public class AccountLockedException extends ApplicationException {
    public static final String ERROR_CODE = "ACCOUNT_LOCKED";
    public static final int HTTP_STATUS_CODE = 403;

    public AccountLockedException(User user) {
        super(ERROR_CODE, HTTP_STATUS_CODE, "Account is locked. Id: " + user.getId());
        super.setPayload(user.getEmail());
    }
}
