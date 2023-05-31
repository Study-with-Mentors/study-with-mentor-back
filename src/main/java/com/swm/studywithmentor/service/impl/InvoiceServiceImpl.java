package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.InvoiceDto;
import com.swm.studywithmentor.model.dto.query.SearchRequest;
import com.swm.studywithmentor.model.dto.query.SearchSpecification;
import com.swm.studywithmentor.model.entity.invoice.Invoice;
import com.swm.studywithmentor.model.entity.invoice.InvoiceStatus;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.repository.EnrollmentRepository;
import com.swm.studywithmentor.repository.InvoiceRepository;
import com.swm.studywithmentor.service.InvoiceService;
import com.swm.studywithmentor.util.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ApplicationMapper applicationMapper;

    @Bean
    private Function<UUID, Invoice> findById() {
        return id -> invoiceRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("NOT_FOUND", HttpStatus.NOT_FOUND, "Not found invoice ID: " + id));
    }

    @Override
    public List<InvoiceDto> searchInvoices(SearchRequest searchRequest) {
        SearchSpecification<Invoice> searchSpecification = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPage(searchRequest.getPage(), searchRequest.getSize());
        return applicationMapper.invoiceToDto(invoiceRepository.findAll(searchSpecification, pageable).get().collect(Collectors.toList()));
    }

    @Override
    public List<InvoiceDto> getInvoices() {
        var invoices = invoiceRepository.findAll();
        return applicationMapper.invoiceToDto(invoices);
    }

    @Override
    public InvoiceDto getInvoiceById(UUID id) {
        var invoice = findById().apply(id);
        return applicationMapper.invoiceToDto(invoice);
    }

    @Override
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        var enrollment = enrollmentRepository.findById(invoiceDto.getEnrollment().getId())
                .orElseThrow(() -> new ApplicationException("NOT_FOUND", HttpStatus.NOT_FOUND, "Not found enrollment ID: " + invoiceDto.getEnrollment().getId()));
        var invoice = applicationMapper.invoiceToEntity(invoiceDto);
        invoice.setEnrollment(enrollment);
        if(invoiceDto.getStatus().equals(InvoiceStatus.PAYED))
            invoice.setPayDate(new Date(System.currentTimeMillis()));

        invoice = invoiceRepository.save(invoice);
        return applicationMapper.invoiceToDto(invoice);
    }

    @Override
    public InvoiceDto updateInvoice(InvoiceDto invoiceDto) {
        var invoice = findById().apply(invoiceDto.getInvoiceId());
        var enrollmentDto = invoiceDto.getEnrollment();
        if(enrollmentDto != null && enrollmentDto.getId() != null && !enrollmentDto.getId().equals(invoice.getEnrollment().getId())) {
            var enrollment = enrollmentRepository.findById(invoiceDto.getEnrollment().getId())
                            .orElseThrow(() -> new ApplicationException("NOT_FOUND", HttpStatus.NOT_FOUND, "Not found enrollment" + enrollmentDto.getEnrollmentDate().toString()));
            invoice.setEnrollment(enrollment);
            invoiceDto.setInvoiceId(enrollment.getId());
            this.deleteInvoice(invoice.getInvoiceId());
            return this.createInvoice(invoiceDto);
        }
        applicationMapper.invoiceToEntity(invoice, invoiceDto);
        invoice = invoiceRepository.save(invoice);
        return applicationMapper.invoiceToDto(invoice);
    }

    @Override
    public void deleteInvoice(UUID id) {
        var invoice = findById().apply(id);
        invoiceRepository.delete(invoice);
    }
}
