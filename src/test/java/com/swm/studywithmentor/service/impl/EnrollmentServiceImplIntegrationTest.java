package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.dto.ResponseObject;
import com.swm.studywithmentor.model.dto.create.EnrollmentCreateDto;
import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.InvoiceService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceImplIntegrationTest {
    @Mock
    private InvoiceService invoiceService;
    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;
    @Mock
    private UserService userService;
    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClazzRepository clazzRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    @Spy
    private ApplicationMapper applicationMapper = new ApplicationMapper(mapper);
    private final UUID enrollmentUUID = UUID.randomUUID();
    private final UUID clazzUUID = UUID.randomUUID();
    private final UUID studentUUID = UUID.randomUUID();
    private final Clazz clazz = Clazz.builder().build();
    private final User student = User.builder().build();

    @Before
    public void setUp() {
        clazz.setId(clazzUUID);
        student.setId(studentUUID);
        var enrollment = Enrollment
                .builder()
                .enrollmentDate(new Date(System.currentTimeMillis()))
                .build();
        enrollment.setId(enrollmentUUID);
        var response = new ResponseObject<>();
        response.setCode(200);
        response.setObject("");
        response.setMessage("");
        doReturn(response)
                .when(invoiceService)
                .createInvoice(eq(PaymentType.VNPAY), isNull(), eq(request));
        when(enrollmentRepository.findById(enrollmentUUID)).thenReturn(Optional.of(enrollment));
        when(clazzRepository.findById(clazzUUID)).thenReturn(Optional.of(clazz));
        when(userRepository.findById(studentUUID)).thenReturn(Optional.of(student));
        when(enrollmentService.getEnrollments()).thenReturn(List.of(new EnrollmentDto(), new EnrollmentDto(), new EnrollmentDto()));
    }

    @Test
    public void givenValidId_whenGetById_thenEnrollmentShouldBeFound() {
        var result = enrollmentService.getEnrollmentById(enrollmentUUID);
        Assertions.assertEquals(enrollmentUUID, result.getId());
    }

    @Test
    public void givenInvalidId_whenGetById_thenShouldThrowNotFoundException() {
        var uuid = UUID.randomUUID();
        Assertions.assertThrows(NotFoundException.class, () -> enrollmentService.getEnrollmentById(uuid));
    }

    @Test
    public void givenNothing_whenGetAllEnrollments_thenReturnRight() {
        int enrollmentQuantity = enrollmentService.getEnrollments().size();
        Assertions.assertEquals(3, enrollmentQuantity);
    }

    @Test
    public void givenInvalidClazzId_whenCreateEnrollment_mustThrowConflictException() {
        var createEnrollment = new EnrollmentCreateDto();
        createEnrollment.setClassId(UUID.randomUUID());
        Assertions.assertThrows(ConflictException.class, () -> enrollmentService.createEnrollment(createEnrollment, request));
    }

    @Test
    public void givenInvalidStudentId_whenCreateEnrollment_mustThrowConflictException() {
        var createEnrollment = new EnrollmentCreateDto();
        createEnrollment.setClassId(clazzUUID);
        createEnrollment.setStudentId(UUID.randomUUID());
        Assertions.assertThrows(ConflictException.class, () -> enrollmentService.createEnrollment(createEnrollment, request));
    }

    @Test
    public void givenValidArgs_whenCreateEnrollment_mustReturnRight() {
        var createEnrollment = new EnrollmentCreateDto();
        createEnrollment.setClassId(clazzUUID);
        createEnrollment.setStudentId(studentUUID);
        createEnrollment.setPaymentType(PaymentType.VNPAY);
        var result = enrollmentService.createEnrollment(createEnrollment, request);
        Mockito.verify(invoiceService).createInvoice(eq(PaymentType.VNPAY), isNull(), eq(request));
        Assertions.assertEquals(200, result.getCode());
    }
}

