package com.swm.studywithmentor.model.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class EntityOptimisticLockingException extends ApplicationException {
    public static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public EntityOptimisticLockingException(Object entity, UUID id, Throwable cause) {
        super(ExceptionErrorCodeConstants.OPTIMISTIC_LOCKING_FAILED,
                HTTP_STATUS,
                String.format("Entity [%s#%s] has been modified or deleted by another transaction",
                        entity.getClass().getName(),
                        id.toString()),
                cause);
        this.setPayload(entity.getClass().getSimpleName() + "#" + id);
    }
}
