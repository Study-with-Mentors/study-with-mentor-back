package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.InvoiceDto;
import com.swm.studywithmentor.model.dto.ResponseObject;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payment/vnpay")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody InvoiceDto invoiceDto, HttpServletRequest req) {
        String paymentURL;
        var resObjBuilder = ResponseObject.builder();
        try {
            paymentURL = paymentService.createPaymentURL(invoiceDto, req);
            resObjBuilder.message("success").code(200).object(paymentURL);
        } catch (ApplicationException exception) {
            log.error(exception.getMessage());
            resObjBuilder.message("failed").code(500).object(exception.getMessage());
            return ResponseEntity.internalServerError().body(resObjBuilder.build());
        }
        return paymentURL.isEmpty() ? ResponseEntity.accepted().build() : ResponseEntity.ok(resObjBuilder.build());
    }

    @GetMapping
    public ResponseEntity<?> paymentResult(HttpServletRequest request) {
        var res = paymentService.paymentReturn(request);
        return ResponseEntity.ok(ResponseObject.builder().code(200).message(res).build());
    }
}
