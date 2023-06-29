package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.dto.EnrollmentReportDto;
import com.swm.studywithmentor.model.dto.ResponseObject;
import com.swm.studywithmentor.model.dto.create.EnrollmentCreateDto;
import com.swm.studywithmentor.model.dto.query.SearchRequest;
import com.swm.studywithmentor.model.dto.search.TimeRangeDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    List<EnrollmentDto> getEnrollments();
    List<EnrollmentDto> searchEnrollments(SearchRequest searchRequest);
    EnrollmentDto getEnrollmentById(UUID id);
    EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto);
    ResponseObject<?> createEnrollment(EnrollmentCreateDto createDto, HttpServletRequest request);
    EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto);
    void deleteEnrollment(UUID id);
    EnrollmentReportDto getEnrollmentReport(TimeRangeDto timeRangeDto);
}
