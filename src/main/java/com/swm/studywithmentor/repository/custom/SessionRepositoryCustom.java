package com.swm.studywithmentor.repository.custom;

import com.swm.studywithmentor.model.entity.course.Course;

public interface SessionRepositoryCustom {
    long findMaxSessionNum(Course course);
    void decreaseSessionNum(Course course, long lower);
    void increaseSessionNum(Course course, long lower);
    void decreaseSessionNum(Course course, long lower, long upper);
    void increaseSessionNum(Course course, long lower, long upper);
}
