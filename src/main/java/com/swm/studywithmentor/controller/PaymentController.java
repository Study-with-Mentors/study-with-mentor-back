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
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/vnpay")
    public ResponseEntity<?> createPayment(@RequestBody InvoiceDto invoiceDto, HttpServletRequest req) {
        String paymentURL;
        var resObjBuilder = ResponseObject.builder();
        paymentURL = paymentService.createPaymentURL(invoiceDto, req);
        resObjBuilder.message("success").code(200).object(paymentURL);
        return paymentURL.isEmpty() ? ResponseEntity.accepted().build() : ResponseEntity.ok(resObjBuilder.build());
    }

    @GetMapping("/vnpay")
    public ResponseEntity<?> paymentResult(HttpServletRequest request) {
        var res = paymentService.paymentReturn(request);
        return ResponseEntity.ok(ResponseObject.builder().code(200).message(res).build());
    }
}
