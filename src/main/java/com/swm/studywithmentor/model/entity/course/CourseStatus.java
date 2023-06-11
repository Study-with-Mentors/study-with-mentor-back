package com.swm.studywithmentor.model.entity.course;

public enum CourseStatus {
    ENABLE, // just be there, it does nothing right now
    DRAFTING, // cannot be viewed by student, allow mentor to modify
    OPEN, // open to view by student, but there is no clazz in the course yet
    CLOSE, // when start new clazz for the course, close for delete or create session
    DISABLE, // cannot open new clazz or modify (only view)
}
