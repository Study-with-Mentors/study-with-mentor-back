package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.InvoiceDto;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface PaymentService {
    String createPaymentURL(InvoiceDto invoiceDto, HttpServletRequest req);
    String paymentReturn(HttpServletRequest req);
    String md5(String message);
    String Sha256(String message);
    String hashAllFields(Map<String, Object> fields);

    String hmacSHA512(String key, String data);

    String getIpAddress(HttpServletRequest request);

}
