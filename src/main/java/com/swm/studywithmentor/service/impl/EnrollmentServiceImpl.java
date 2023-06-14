package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.dto.query.SearchRequest;
import com.swm.studywithmentor.model.dto.query.SearchSpecification;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.service.EnrollmentService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final ApplicationMapper applicationMapper;
    private final Function<UUID, Enrollment> findById;
    private final UserService userService;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, ApplicationMapper applicationMapper, UserService userService) {
        this.enrollmentRepository = enrollmentRepository;
        this.applicationMapper = applicationMapper;
        this.userService = userService;
        findById = id -> enrollmentRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("NOT_FOUND", HttpStatus.NOT_FOUND , "Not found enrollment " + id.toString()));
    }

    @Override
    public List<EnrollmentDto> getEnrollments() {
        var enrollments = enrollmentRepository.findAll();
        return applicationMapper.enrollmentToDto(enrollments);
    }

    @Override
    public List<EnrollmentDto> searchEnrollments(SearchRequest searchRequest) {
        SearchSpecification<Enrollment> searchSpecification = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPage(searchRequest.getPage(), searchRequest.getSize());
        var result = enrollmentRepository.findAll(searchSpecification, pageable);
        return applicationMapper.enrollmentToDto(result.get().toList());
    }

    @Override
    public EnrollmentDto getEnrollmentById(UUID id) {
        var enrollment = findById.apply(id);
        return applicationMapper.enrollmentToDto(List.of(enrollment)).get(0);
    }

    @Override
    public EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto) {
        var enrollment = applicationMapper.enrollmentToEntity(enrollmentDto);
        User user = userService.getCurrentUser();
        enrollment.setStudent(user);
        enrollment = enrollmentRepository.save(enrollment);
        return applicationMapper.enrollmentToDto(enrollment);
    }

    @Override
    public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto) {
        var enrollment = findById.apply(enrollmentDto.getId());
        User user = userService.getCurrentUser();
        // FIXME: Optimistic locking
        if(!enrollment.getStudent().getId().equals(user.getId())) {
            throw new ConflictException(Enrollment.class, ActionConflict.UPDATE, "User does not own this enrollment", user.getId());
        }
        applicationMapper.enrollmentToEntity(enrollment, enrollmentDto);
        enrollment = enrollmentRepository.save(enrollment);

        return applicationMapper.enrollmentToDto(enrollment);
    }

    @Override
    public void deleteEnrollment(UUID id) {
        var enrollment = findById.apply(id);
        User user = userService.getCurrentUser();
        if(!enrollment.getStudent().getId().equals(user.getId())) {
            throw new ConflictException(Enrollment.class, ActionConflict.DELETE, "User does not own this enrollment", user.getId());
        }
        enrollmentRepository.delete(enrollment);
    }
}
