package com.ervingorospe.grab_booking_service.model.DTO;

import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.validate.phoneNumber.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;


public record CustomerDTO(
    @JsonIgnore
    @NotNull(message = "User id can't be empty")
    String id,

    @NotNull(message = "Firstname can't be empty")
    @JsonProperty("first_name")
    String firstName,

    @NotNull(message = "Lastname can't be empty")
    @JsonProperty("last_name")
    String lastName,

    @JsonIgnore
    @JsonProperty("email")
    @NotNull(message = "Email can't be empty")
    String email,

    @JsonProperty("contact_number")
    @NotNull(message = "Contact number can't be empty")
    @PhoneNumber(message = "Provide a valid Phone number")
    String contactNumber,

    @JsonIgnore
    @NotNull(message = "Role can't be empty")
    String role
) {
    public CustomerDTO(Customer customer) {
        this(
            customer.getId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getContactNumber(),
            customer.getRole()
        );
    }
}
