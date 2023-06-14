package com.swm.studywithmentor.model.dto.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swm.studywithmentor.model.entity.course.CourseIntendedLearner;
import com.swm.studywithmentor.model.entity.course.CourseLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CourseSearchDto extends BaseSearchDto{
    private String name = "";
    @JsonIgnore
    private CourseIntendedLearner intendedLearner = null;
    @JsonIgnore
    private CourseLevel courseLevel;
    @JsonIgnore
    private List<String> fieldNames;
    private UUID mentorId;
    private int page;

    // alias for fieldNames in URL as field
    public void setField(List<String> fields) {
        fieldNames = fields;
    }

    // alias for `courseLevel` as `level`
    public void setLevel(CourseLevel level) {
        courseLevel = level;
    }

    // alias for `intendedLearner` as `learner`
    public void setLearner(CourseIntendedLearner learner) {
        intendedLearner = learner;
    }
}
