package com.swm.studywithmentor.model.exception;

public class EntityOptimisticLockingException extends ApplicationException {
    public static final String ERROR_CODE = "OPTIMISTIC_LOCKING_FAILED";
    public static final int HTTP_STATUS_CODE = 400;

    public EntityOptimisticLockingException(Object entity, long id, Throwable cause) {
        super(ERROR_CODE,
            HTTP_STATUS_CODE,
            String.format("Entity [%s#%d] has been modified or deleted by another transaction",
                entity.getClass().getName(),
                id),
            cause);
        this.setPayload(entity.getClass().getSimpleName() + "#" + id);
    }
}
