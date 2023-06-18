package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.InvoiceDto;
import com.swm.studywithmentor.model.dto.ResponseObject;
import com.swm.studywithmentor.model.dto.query.SearchRequest;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    List<InvoiceDto> searchInvoices(SearchRequest searchRequest);
    List<InvoiceDto> getInvoices();
    InvoiceDto getInvoiceById(UUID id);
    InvoiceDto createInvoice(InvoiceDto invoiceDto);
    InvoiceDto updateInvoice(InvoiceDto invoiceDto);
    void deleteInvoice(UUID id);

    ResponseObject<?> createInvoice(PaymentType paymentType, Enrollment enrollment, HttpServletRequest request);
}
