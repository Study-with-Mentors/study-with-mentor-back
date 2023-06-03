package com.swm.studywithmentor.configuration;

import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.invoice.Invoice;
import com.swm.studywithmentor.model.entity.invoice.InvoiceStatus;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import com.swm.studywithmentor.model.entity.user.Role;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.repository.ClazzRepository;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.InvoiceRepository;
import com.swm.studywithmentor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SeedConfiguration {
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ClazzRepository clazzRepository;
    private final InvoiceRepository invoiceRepository;
    @Bean
    public void seedUser() {
        var users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            var user = User.builder()
                    .email("test" + i)
                    .password("test" + i)
                    .lastName("test" + i)
                    .firstName("test" + i)
                    .role(Role.NONE)
                    .build();
            users.add(user);
        }
        userRepository.saveAll(users).forEach(u -> log.info("UserID: " + u.getId()));
    }

    @Bean
    public void seedEnrollment() {
        var users = userRepository.findAll();
        var enrollments = new ArrayList<Enrollment>();
        for(User user : users) {
            var enrollment = Enrollment.builder()
                    .status(EnrollmentStatus.NONE)
                    .student(user)
                    .build();
            enrollments.add(enrollment);
        }
        enrollmentRepository.saveAll(enrollments).forEach(e -> log.info("EnrollmentID : " + e.getId()));
    }

    @Bean
    public void seedClass() {
        var enrollments = enrollmentRepository.findAll();
        var clazz = Clazz.builder()
                .price(100000F)
                .build();
        clazz = clazzRepository.save(clazz);
        for(Enrollment enrollment : enrollments)
            enrollment.setClazz(clazz);
        enrollmentRepository.saveAll(enrollments);
    }

    @Bean
    public void seedInvoice() {
        var enrollments = enrollmentRepository.findAll();
        var invoices = new ArrayList<Invoice>();
        for (Enrollment enrollment : enrollments) {
            var invoice = Invoice.builder()
                    .status(InvoiceStatus.NONE)
                    .type(PaymentType.VNPAY)
                    .enrollment(enrollment)
                    .totalPrice(enrollment.getClazz().getPrice())
                    .build();
            invoices.add(invoice);
        }
        invoiceRepository.saveAll(invoices).forEach(i -> log.info("Invoice ID: {}", i.getInvoiceId() ));
    }

}
