package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.entity.ClazzStatus;
import com.swm.studywithmentor.model.entity.course.CourseIntendedLearner;
import com.swm.studywithmentor.model.entity.course.CourseLevel;
import com.swm.studywithmentor.model.entity.course.CourseStatus;
import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.invoice.InvoiceStatus;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import com.swm.studywithmentor.model.entity.session.SessionType;
import com.swm.studywithmentor.model.entity.user.Education;
import com.swm.studywithmentor.model.entity.user.Gender;
import com.swm.studywithmentor.model.entity.user.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/enums")
public class EnumsController {

    @GetMapping("/course/status")
    public ResponseEntity<?> getCourseStatus() {
        return ResponseEntity.ok(CourseStatus.values());
    }

    @GetMapping("/course/level")
    public ResponseEntity<?> getCourseLevel() {
        return ResponseEntity.ok(CourseLevel.values());
    }

    @GetMapping("/course/intendtedLeanrer")
    public ResponseEntity<?> getIntendtedLearner() {
        return ResponseEntity.ok(CourseIntendedLearner.values());
    }

    @GetMapping("/enrollment/status")
    public ResponseEntity<?> getEnrollmentStatus() {
        return ResponseEntity.ok(EnrollmentStatus.values());
    }

    @GetMapping("/invoice/status")
    public ResponseEntity<?> getInvoiceStatus() {
        return ResponseEntity.ok(InvoiceStatus.values());
    }

    @GetMapping("/invoice/payment-type")
    public ResponseEntity<?> getPaymentType() {
        return ResponseEntity.ok(PaymentType.values());
    }

    @GetMapping("/session/type")
    public ResponseEntity<?> getSessionType() {
        return ResponseEntity.ok(SessionType.values());
    }

    @GetMapping("/user/education")
    public ResponseEntity<?> getUserEducation() {
        return ResponseEntity.ok(Education.values());
    }

    @GetMapping("/user/gender")
    public ResponseEntity<?> getUserGender() {
        return ResponseEntity.ok(Gender.values());
    }

    @GetMapping("/user/role")
    public ResponseEntity<?> getUserRole() {
        var roles = Arrays.stream(Role.values()).filter(r -> r.compareTo(Role.ADMIN) != 0).toList();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/clazz/status")
    public ResponseEntity<?> getClazzStatus() {
        return ResponseEntity.ok(ClazzStatus.values());
    }
}
