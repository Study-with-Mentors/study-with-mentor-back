package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.EnrollmentService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@Transactional
//TODO: validate ID format
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final ApplicationMapper applicationMapper;
    private final Function<UUID, Enrollment> findById;
    private final UserRepository userRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, ApplicationMapper applicationMapper, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.applicationMapper = applicationMapper;
        this.userRepository = userRepository;
        findById = id -> enrollmentRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("NOT_FOUND", 404 , "Not found enrollment " + id.toString()));
    }

    @Override
    public List<EnrollmentDto> getEnrollments() {
        //TODO: use specification and pagination
        var enrollments = enrollmentRepository.findAll();
        return applicationMapper.enrollmentToDto(enrollments);
    }

    @Override
    public EnrollmentDto getEnrollmentById(UUID id) {
        var enrollment = findById.apply(id);
        return applicationMapper.enrollmentToDto(List.of(enrollment)).get(0);
    }

    @Override
    public EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto) {
        var enrollment = applicationMapper.enrollmentToEntity(enrollmentDto);
        //TODO: use UserService to get user
        var user = userRepository.findById(enrollmentDto.getUser().getId())
                .orElseThrow(() -> new ApplicationException("Not Found", 404, "Not found user: " + enrollmentDto.getUser().getId().toString()));
        enrollment.setUser(user);
        enrollment = enrollmentRepository.save(enrollment);
        return applicationMapper.enrollmentToDto(enrollment);
    }

    @Override
    public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto) {
        var enrollment = findById.apply(enrollmentDto.getId());
        if(enrollmentDto.getUser().getId().toString() != null && !enrollment.getUser().getId().toString().equals(enrollmentDto.getUser().getId().toString())) {
            //TODO: use UserService to get user
            var newUser = userRepository.findById(enrollmentDto.getUser().getId())
                    .orElseThrow(() -> new ApplicationException("Not Found", 404, "Not found user: " + enrollmentDto.getUser().getId().toString()));
            enrollment.setUser(newUser);
        }
        applicationMapper.enrollmentToEntity(enrollment, enrollmentDto);
        enrollment = enrollmentRepository.save(enrollment);

        return applicationMapper.enrollmentToDto(enrollment);
    }

    @Override
    public void deleteEnrollment(UUID id) {
        var enrollment = findById.apply(id);
        enrollmentRepository.delete(enrollment);
    }
}
