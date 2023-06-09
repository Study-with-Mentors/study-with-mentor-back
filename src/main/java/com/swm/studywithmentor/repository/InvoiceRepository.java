package com.swm.studywithmentor.repository;

import com.swm.studywithmentor.model.entity.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID>,
        JpaSpecificationExecutor<Invoice> {
}
