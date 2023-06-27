package com.swm.studywithmentor.configuration;

import com.swm.studywithmentor.model.entity.Clazz;
import com.swm.studywithmentor.model.entity.Field;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.course.CourseLevel;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.invoice.Invoice;
import com.swm.studywithmentor.model.entity.invoice.InvoiceStatus;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import com.swm.studywithmentor.model.entity.user.Mentor;
import com.swm.studywithmentor.model.entity.user.Role;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.repository.*;
import com.swm.studywithmentor.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SeedConfiguration {
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ClazzRepository clazzRepository;
    private final FieldRepository fieldRepository;
    private final MentorRepository mentorRepository;
    private final InvoiceRepository invoiceRepository;
    private final CourseRepository courseRepository;

    @Bean
    public void seedField() {
        var fields = new ArrayList<Field>();
        for (int i = 0; i < 5; i++) {
            var field = Field.builder()
                    .name("Test" + i)
                    .code("00" + i)
                    .description("Description" + i)
                    .build();
            fields.add(field);
        }
        fieldRepository.saveAll(fields).forEach(f -> log.info("fieldID: " + f.getId()));
    }
    @Bean
    public List<User> seedUser(PasswordEncoder passwordEncoder) {
        var users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            var user = User.builder()
                    .email("test" + i)
                    .password(passwordEncoder.encode("test" + i))
                    .lastName("test" + i)
                    .firstName("test" + i)
                    .role(Role.NONE)
                    .build();
            users.add(user);
        }
        var added = userRepository.saveAll(users);
        added.forEach(u -> log.info("UserID: {}", u.getId()));
        return added;
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
        log.info("Class ID: {}",  clazzRepository.save(clazz).getId());
        for(Enrollment enrollment : enrollments)
            enrollment.setClazz(clazz);
        enrollmentRepository.saveAll(enrollments);
    }

    @Bean
    public void seedMentor()
    {
        var users = userRepository.findAll();
        var mentors = new ArrayList<Mentor>();
        for (User user : users) {
            var mentor = Mentor
                    .builder()
                    .user(user)
                    .bio("This is bio")
                    .build();
            mentors.add(mentor);
            user.setMentor(mentor);
        }
        mentorRepository.saveAll(mentors).forEach(m -> log.info("MentorID: " + m.getMentorId()));
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

    @Bean
    public void seedCourse() {
        var user = userRepository.findAll();
        var field = fieldRepository.findAll();
        var course = Course.builder()
                .courseLevel(CourseLevel.FUNDAMENTAL)
                .description("This course for test")
                .field(field.get(0))
                .mentor(user.get(0))
                .learningOutcome("Test")
                .shortName("test")
                .build();
        log.info("CourseID: " + courseRepository.save(course).getId());
    }
}
