package com.swm.studywithmentor.util;

import com.swm.studywithmentor.model.dto.EnrollmentDto;
import com.swm.studywithmentor.model.dto.InvoiceDto;
import com.swm.studywithmentor.model.dto.UserDto;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.invoice.Invoice;
import com.swm.studywithmentor.model.entity.user.User;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApplicationMapper {
    private final ModelMapper mapper;

    @Autowired
    public ApplicationMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<EnrollmentDto> enrollmentToDto (List<Enrollment> enrollments) {
        return enrollments.stream().map(this::enrollmentToDto).collect(Collectors.toList());
    }

    public EnrollmentDto enrollmentToDto (Enrollment enrollment) {
        return mapper.typeMap(Enrollment.class, EnrollmentDto.class)
                .addMappings(mapping -> mapping.map(src -> src.getInvoice().getInvoiceId(), (destination, value) -> destination.setInvoice((UUID) value)))
                .map(enrollment);
    }

    public List<UserDto> userToDto(List<User> users) {
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    public UserDto userToDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public List<InvoiceDto> invoiceToDto(List<Invoice> invoices) {
        return invoices.stream().map(this::invoiceToDto).collect(Collectors.toList());
    }
    public InvoiceDto invoiceToDto(Invoice invoice) {
        return mapper.typeMap(Invoice.class, InvoiceDto.class)
                //TODO: Total price will be mapped when Clazz finish implemented.
                .addMappings(mapper -> mapper.skip(InvoiceDto::setTotalPrice))
                .map(invoice);
    }

    public List<Enrollment> enrollmentToEntity(List<EnrollmentDto> enrollmentDtos) {
        return enrollmentDtos.stream().map(this::enrollmentToEntity).collect(Collectors.toList());
    }

    public void enrollmentToEntity(Enrollment enrollment, EnrollmentDto enrollmentDto) {
        mapper.map(enrollmentDto, enrollment);
    }

    public Enrollment enrollmentToEntity(EnrollmentDto enrollmentDto) {
        return mapper.map(enrollmentDto, Enrollment.class);
    }

    public List<User> userToEntity(List<UserDto> userDtos) {
        return userDtos.stream().map(this::userToEntity).collect(Collectors.toList());
    }

    public User userToEntity(UserDto user) {
        return mapper.map(user, User.class);
    }

    public List<Invoice> invoiceToEntity(List<InvoiceDto> invoices) {
        return invoices.stream().map(this::invoiceToEntity).collect(Collectors.toList());
    }
    public Invoice invoiceToEntity(InvoiceDto invoice) {
        return mapper.typeMap(InvoiceDto.class, Invoice.class)
                .addMappings(mapper -> mapper.skip(Invoice::setTotalPrice))
                .map(invoice);
    }

    public void invoiceToEntity(Invoice invoice, InvoiceDto invoiceDto) {
        mapper.map(invoiceDto, invoice);
    }

}
