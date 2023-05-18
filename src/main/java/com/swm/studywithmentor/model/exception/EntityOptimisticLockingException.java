package com.swm.studywithmentor.model.exception;

public class EntityOptimisticLockingException extends ApplicationException {
    public static final String ERROR_CODE = "OPTIMISTIC_LOCKING_FAILED";

    public EntityOptimisticLockingException(Object entity, long id, Throwable cause) {
        super(ERROR_CODE,
            String.format("Entity [%s#%d] has been modified or deleted by another transaction",
                entity.getClass().getName(),
                id),
            cause);
        this.setPayload(entity.getClass().getSimpleName() + "#" + id);
    }
}
