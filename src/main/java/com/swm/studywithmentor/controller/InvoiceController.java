package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.InvoiceDto;
import com.swm.studywithmentor.model.dto.query.SearchRequest;
import com.swm.studywithmentor.model.entity.invoice.InvoiceStatus;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.service.InvoiceService;
import com.swm.studywithmentor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/invoice")
@Slf4j
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable String id) {
        try {
            UUID invoiceId = UUID.fromString(id);
            return ResponseEntity.ok(invoiceService.getInvoiceById(invoiceId));
        } catch (ApplicationException e) {
            log.error(e.getMessage());
            if(e.getStatus() == HttpStatus.NOT_FOUND)
                return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("ID: " + id + " is wrong format");
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchInvoices(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(invoiceService.searchInvoices(searchRequest));
    }
    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceDto invoiceDto, HttpServletRequest request) {
        try {
            var invoice = invoiceService.createInvoice(invoiceDto);
            if(invoice.getStatus() != null && invoice.getStatus() == InvoiceStatus.NONE && invoice.getPaymentType() == PaymentType.VNPAY) {
                return ResponseEntity.ok(paymentService.createPaymentURL(invoice, request));
            }
            return ResponseEntity.created(new URI("/api/invoice/" + invoice.getInvoiceId().toString())).build();
        } catch (ApplicationException exception) {
            log.error(exception.getMessage());
            if(exception.getStatus() == HttpStatus.NOT_FOUND)
                return ResponseEntity.notFound().build();
            return ResponseEntity.badRequest().build();
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable String id, @RequestBody InvoiceDto invoiceDto) {
        try {
            UUID invoiceId = UUID.fromString(id);
            invoiceDto.setInvoiceId(invoiceId);
            var invoice = invoiceService.updateInvoice(invoiceDto);
            return ResponseEntity.ok(invoice);
        } catch (ApplicationException a) {
            log.error(a.getMessage());
            if(a.getStatus() == HttpStatus.NOT_FOUND)
                return ResponseEntity.notFound().build();
            return ResponseEntity.internalServerError().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("ID: " + id + " is wrong format");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable String id) {
        try {
            UUID invoiceId = UUID.fromString(id);
            invoiceService.deleteInvoice(invoiceId);
            return ResponseEntity.accepted().build();
        } catch (ApplicationException a) {
            log.error(a.getMessage());
            if(a.getStatus() == HttpStatus.NOT_FOUND)
                return ResponseEntity.notFound().build();
            return ResponseEntity.internalServerError().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("ID: " + id + " is wrong format");
        }
    }
}
