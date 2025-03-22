package com.ervingorospe.grab_booking_service.model.DTO;

import com.ervingorospe.grab_booking_service.model.entity.Customer;

public record CustomerResponseDTO(
        String id,
        String firstName,
        String lastName,
        String contactNumber
) {
    public CustomerResponseDTO(Customer customer) {
        this(
            customer.getId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getContactNumber()
        );
    }
}
