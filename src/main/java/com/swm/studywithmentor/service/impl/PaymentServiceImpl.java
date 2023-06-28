package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.configuration.PaymentProperties;
import com.swm.studywithmentor.model.dto.InvoiceDto;
import com.swm.studywithmentor.model.dto.payment.VNPayStatus;
import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.invoice.Invoice;
import com.swm.studywithmentor.model.entity.invoice.InvoiceStatus;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import com.swm.studywithmentor.model.exception.ActionConflict;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.model.exception.ConflictException;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.InvoiceRepository;
import com.swm.studywithmentor.service.PaymentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@Data
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentProperties paymentProperties;
    private final InvoiceRepository invoiceRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            digest = createHashedString(message, md);
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
            throw new ApplicationException("internal server error", HttpStatus.INTERNAL_SERVER_ERROR, "Encoding is not supported");
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getMessage());
            throw new ApplicationException("internal server error", HttpStatus.INTERNAL_SERVER_ERROR, "No Algorithm");
        }
        return digest;
    }

    @Override
    public String Sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            digest = createHashedString(message, md);
        }catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
            throw new ApplicationException("internal server error", HttpStatus.INTERNAL_SERVER_ERROR, "Encoding is not supported");
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getMessage());
            throw new ApplicationException("internal server error", HttpStatus.INTERNAL_SERVER_ERROR, "No Algorithm");
        }
        return digest;
    }

    private String createHashedString(String message, MessageDigest md) throws UnsupportedEncodingException {
        String digest;
        byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        digest = sb.toString();
        return digest;
    }

    @Override
    public String hashAllFields(Map<String, Object> fields) {
        List<String> fieldNames = new ArrayList<String>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return hmacSHA512(paymentProperties.getVnpHashSecret(),sb.toString());
    }

    @Override
    public String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getLocalAddr();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApplicationException("internal server error", HttpStatus.INTERNAL_SERVER_ERROR, "Invalid IP");
        }
        return ipAdress;
    }

    public String createPaymentURL(InvoiceDto invoiceDto, HttpServletRequest req)  {
        var find = invoiceRepository.findById(invoiceDto.getInvoiceId())
                .orElseThrow(() -> new ConflictException(this.getClass(), ActionConflict.CREATE, "Not found clazz", invoiceDto.getInvoiceId()));
        if(!isValidInvoice(find))
            return "";
        //VNPay only support VND
        //multiply with 100 is required by VNPay
        long amount = (long)(Math.ceil(find.getTotalPrice()) * 100);
        String bankCode = req.getParameter("bankCode");

        String vnpTxnRef = find.getInvoiceId().toString();
        String vnpIpAddr = this.getIpAddress(req);
        String vnpTmnCode = paymentProperties.getVnpTmnCode();

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", PaymentProperties.vnpVersion);
        vnpParams.put("vnp_Command", PaymentProperties.vnpCommand);
        vnpParams.put("vnp_TmnCode", vnpTmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParams.put("vnp_BankCode", bankCode);
        }
        vnpParams.put("vnp_TxnRef", vnpTxnRef);
        vnpParams.put("vnp_OrderInfo", "Thanh toan hoa don: " + vnpTxnRef);
        vnpParams.put("vnp_OrderType", "other");
        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnpParams.put("vnp_Locale", locate);
        } else {
            vnpParams.put("vnp_Locale", "vn");
        }
        vnpParams.put("vnp_ReturnUrl", paymentProperties.getVnpReturnURL());
        vnpParams.put("vnp_IpAddr", vnpIpAddr);

        Calendar cld = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_CreateDate", vnpCreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnpExpireDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_ExpireDate", vnpExpireDate);

        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnpParams.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = this.hmacSHA512(paymentProperties.getVnpHashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return PaymentProperties.vnpPayUrl + "?" + queryUrl;
    }
    private boolean isValidInvoice(Invoice invoice) {
        if(invoice.getType() != PaymentType.VNPAY) {
            log.info("Invoice use a different payment method");
            throw new ApplicationException("Bad request", HttpStatus.BAD_REQUEST, "Invoice use a different payment method");
        }
        if(invoice.getStatus() == InvoiceStatus.PAYED) {
            var message = "Invoice " + invoice.getInvoiceId() + " is already payed";
            log.info(message);
            throw new ApplicationException("Bad request", HttpStatus.BAD_REQUEST, message);
        }
        if(invoice.getStatus() == InvoiceStatus.CANCELLED) {
            var message = "Invoice " + invoice.getInvoiceId() + " is " + InvoiceStatus.CANCELLED.name();
            log.info(message);
            throw new ApplicationException("Bad request", HttpStatus.BAD_REQUEST, message);
        }
        return true;
    }

    public String paymentReturn(HttpServletRequest req) {
        String message = "Transaction fail";
        String responseCode = "V" + req.getParameter("vnp_ResponseCode");
        if(!VNPayStatus.isVNPayStatus(responseCode))
            return message;
        UUID invoiceId = UUID.fromString(req.getParameter("vnp_TxnRef"));
        var invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ConflictException(this.getClass(), ActionConflict.UPDATE, "Cannot found invoice", invoiceId));
        if(VNPayStatus.valueOf(responseCode) == VNPayStatus.V00) {
            float totalPrice = Float.parseFloat(req.getParameter("vnp_Amount"))/100;
            invoice.setPayDate(new Date(System.currentTimeMillis()));
            invoice.setType(PaymentType.VNPAY);
            invoice.setStatus(InvoiceStatus.PAYED);
            invoice.setTotalPrice(totalPrice);
            var enrollment = enrollmentRepository.findById(invoice.getEnrollment().getId()).orElse(null);
            if(enrollment != null) {
                enrollment.setStatus(EnrollmentStatus.ENROLLED);
                enrollmentRepository.save(enrollment);
            }
            invoiceRepository.save(invoice);
        }
        message = VNPayStatus.valueOf(responseCode).getMessage();
        return message;
    }
}
