package com.ervingorospe.grab_booking_service.model.DTO;

import com.ervingorospe.grab_booking_service.model.entity.Booking;

import java.time.LocalDateTime;

public record BookingDTO(
    String id,
    CustomerResponseDTO customer,
    AddressDTO pickupLocation,
    AddressDTO dropOffLocation,
    CustomerResponseDTO driver,
    LocalDateTime bookingTime,
    LocalDateTime pickupTime,
    LocalDateTime arrivalTime,
    String status
) {
    public BookingDTO(Booking booking) {
        this(
            booking.getId(),
            new CustomerResponseDTO(booking.getCustomer()),
            booking.getPickupLocation(),
            booking.getDropOffLocation(),
            booking.getDriver() != null ? new CustomerResponseDTO(booking.getDriver()) : null,
            booking.getBookingTime(),
            booking.getPickupTime(),
            booking.getArrivalTime(),
            booking.getStatus()
        );
    }
}
