package com.ervingorospe.grab_booking_service.model.entity;

import com.ervingorospe.grab_booking_service.model.DTO.AddressDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "booking")
public class Booking {
    @Id
    private String id;
    private String customerId;
    private String driverId;
    Customer customer;
    AddressDTO pickupLocation;
    AddressDTO dropOffLocation;
    Customer driver;
    LocalDateTime bookingTime;
    LocalDateTime pickupTime;
    LocalDateTime arrivalTime;
    private String status;

    public Booking() {
        this.id = UUID.randomUUID().toString();
    }

    public Booking(String id, String customerId, String driverId, Customer customer, AddressDTO pickupLocation, AddressDTO dropOffLocation, Customer driver, LocalDateTime bookingTime, LocalDateTime pickupTime, LocalDateTime arrivalTime, String status) {
        this.id = id;
        this.customerId = customerId;
        this.driverId = driverId;
        this.customer = customer;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.driver = driver;
        this.bookingTime = bookingTime;
        this.pickupTime = pickupTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }

    public Booking(String id, String customerId, Customer customer, AddressDTO pickupLocation, AddressDTO dropOffLocation, LocalDateTime bookingTime, String status) {
        this.id = id;
        this.customerId = customerId;
        this.customer = customer;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AddressDTO getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(AddressDTO pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public AddressDTO getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(AddressDTO dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public Customer getDriver() {
        return driver;
    }

    public void setDriver(Customer driver) {
        this.driver = driver;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
