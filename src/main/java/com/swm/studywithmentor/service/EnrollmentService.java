package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.dto.query.SearchRequest;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    List<EnrollmentDto> getEnrollments();
    List<EnrollmentDto> searchEnrollments(SearchRequest searchRequest);
    EnrollmentDto getEnrollmentById(UUID id);
    EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto);
    EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto);
    void deleteEnrollment(UUID id);
}
