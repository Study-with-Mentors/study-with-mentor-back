package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.EnrollmentDto;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    List<EnrollmentDto> getEnrollments();
    EnrollmentDto getEnrollmentById(UUID id);
    EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto);
    EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto);
    void deleteEnrollment(UUID id);
}
