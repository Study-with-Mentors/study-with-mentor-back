package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.InvoiceDto;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    List<InvoiceDto> getInvoices();
    InvoiceDto getInvoiceById(UUID id);
    InvoiceDto createInvoice(InvoiceDto invoiceDto);
    InvoiceDto updateInvoice(InvoiceDto invoiceDto);
    void deleteInvoice(UUID id);
}
