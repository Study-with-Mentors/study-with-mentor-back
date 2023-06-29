package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.dto.EnrollmentReportDto;
import com.swm.studywithmentor.model.dto.ResponseObject;
import com.swm.studywithmentor.model.dto.create.EnrollmentCreateDto;
import com.swm.studywithmentor.model.dto.query.SearchRequest;
import com.swm.studywithmentor.model.dto.query.SearchSpecification;
import com.swm.studywithmentor.model.dto.search.TimeRangeDto;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.EnrollmentService;
import com.swm.studywithmentor.service.InvoiceService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final InvoiceService invoiceService;
    private final UserRepository userRepository;
    private final ClazzRepository clazzRepository;
    private final ApplicationMapper applicationMapper;
    private final Function<UUID, Enrollment> findById;
    private final UserService userService;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, InvoiceService invoiceService, UserRepository userRepository, ClazzRepository clazzRepository, ApplicationMapper applicationMapper, UserService userService) {
        this.enrollmentRepository = enrollmentRepository;
        this.invoiceService = invoiceService;
        this.userRepository = userRepository;
        this.clazzRepository = clazzRepository;
        this.applicationMapper = applicationMapper;
        this.userService = userService;
        findById = id -> enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.getClass(), id));
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
    public ResponseObject<?> createEnrollment(EnrollmentCreateDto createDto, HttpServletRequest request) {
        var clazz = clazzRepository.findById(createDto.getClassId())
                .orElseThrow(() -> new ConflictException(Enrollment.class, ActionConflict.CREATE, "Clazz not found", createDto.getClassId()));
        User student = userRepository.findById(userService.getCurrentUser().getId())
                .orElseThrow(() -> new ConflictException(Enrollment.class, ActionConflict.CREATE, "Student not found", userService.getCurrentUser().getId()));
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByClazzAndStudent(clazz, student);
        Enrollment enrollment;
        if (enrollmentOpt.isPresent()) {
            enrollment = enrollmentOpt.get();
            if (enrollment.getStatus() == EnrollmentStatus.ENROLLED) {
                throw new ApplicationException("FOUND", HttpStatus.FOUND, "Already in enrolled");
            }
        } else {
            enrollment = Enrollment.builder()
                    .student(student)
                    .enrollmentDate(new Date(System.currentTimeMillis()))
                    .clazz(clazz)
                    .status(EnrollmentStatus.NONE)
                    .build();
            enrollment = enrollmentRepository.save(enrollment);
        }
        return invoiceService.createInvoice(createDto.getPaymentType(), enrollment, request);
    }

    @Override
    public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto) {
        var enrollment = findById.apply(enrollmentDto.getId());
        User user = userService.getCurrentUser();
        if (!enrollment.getStudent().getId().equals(user.getId())) {
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
        if (!enrollment.getStudent().getId().equals(user.getId())) {
            throw new ConflictException(Enrollment.class, ActionConflict.DELETE, "User does not own this enrollment", user.getId());
        }
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public EnrollmentReportDto getEnrollmentReport(TimeRangeDto timeRangeDto) {
        User user = userService.getCurrentUser();
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentInTimeRange(timeRangeDto, user.getId());
        EnrollmentReportDto reportDto = new EnrollmentReportDto();
        reportDto.setNumOfEnrollments(enrollments.size());
        reportDto.setTotalEarning(enrollments.stream()
                .mapToDouble(e -> e.getInvoice().getTotalPrice())
                .sum()
        );
        return reportDto;
    }
}
