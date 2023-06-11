package com.swm.studywithmentor.model.entity;

public enum ClazzStatus {
    OPEN, // Open for enrollment
    CLOSE, // Close for enrollment, when mentor explicitly set or pass the close date for enrollment
    IN_PROGRESS, // when the clazz is learning
    CANCEL, // Close for some reason
    COMPLETE, // The clazz is finished after the last lesson
}
