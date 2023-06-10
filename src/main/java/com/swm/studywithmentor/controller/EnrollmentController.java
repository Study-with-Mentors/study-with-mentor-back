package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.dto.query.SearchRequest;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnrollment(@PathVariable String id) {
        try {
            UUID enrollmentId = UUID.fromString(id);
            var enrollments = enrollmentService.getEnrollmentById(enrollmentId);
            return ResponseEntity.ok(enrollments);
        } catch (ApplicationException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("ID: " + id + " is wrong format");
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchEnrollments(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(enrollmentService.searchEnrollments(searchRequest));
    }

    @PostMapping
    public ResponseEntity<?> createEnrollment(@RequestBody EnrollmentDto enrollmentDto) {
        try {
            var enrollment = enrollmentService.createEnrollment(enrollmentDto);
            if(enrollment != null) {
                URI uri = new URI("/api/enrollment/" + enrollment.getId().toString());
                return ResponseEntity.created(uri).build();
            }
        } catch (ApplicationException e) {
            log.error(e.getMessage());
            return  ResponseEntity.internalServerError().body("Cannot create enrollment");
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollment(@PathVariable String id, @RequestBody EnrollmentDto enrollmentDto) {
        try {
            UUID enrollmentId = UUID.fromString(id);
            enrollmentDto.setId(enrollmentId);
            var enrollment = enrollmentService.updateEnrollment(enrollmentDto);
            return ResponseEntity.ok(enrollment);
        } catch (ApplicationException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable String id) {
        //TODO: add `bit` field to enrollment for soft deleting.
        return null;
    }
}
