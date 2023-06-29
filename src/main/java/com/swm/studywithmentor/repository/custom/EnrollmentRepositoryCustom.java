package com.swm.studywithmentor.repository.custom;

import com.swm.studywithmentor.model.dto.search.TimeRangeDto;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepositoryCustom {
    List<Enrollment> getEnrollmentInTimeRange(TimeRangeDto dto, UUID mentorId);
}
