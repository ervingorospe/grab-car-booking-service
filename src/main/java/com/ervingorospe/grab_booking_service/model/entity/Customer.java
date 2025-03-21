package com.ervingorospe.grab_booking_service.model.entity;

import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String contactNumber;

    public Customer() {
    }

    public Customer(String id, String firstName, String lastName, String email, String contactNumber, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.role = role;
    }

    public Customer(BookingRequestDTO bookingRequestDTO) {
        this.id = bookingRequestDTO.customer().id();
        this.email = bookingRequestDTO.customer().email();
        this.firstName = bookingRequestDTO.customer().firstName();
        this.lastName = bookingRequestDTO.customer().lastName();
        this.contactNumber = bookingRequestDTO.customer().contactNumber();
        this.role = bookingRequestDTO.customer().role();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
