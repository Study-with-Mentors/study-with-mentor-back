package com.swm.studywithmentor.util;

import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseStatus;

public class Utils {
    private Utils() {}

    public static boolean isCourseOpenForEdit(Course course) {
        return course.getStatus() == CourseStatus.OPEN || course.getStatus() == CourseStatus.DRAFTING;
    }
}
