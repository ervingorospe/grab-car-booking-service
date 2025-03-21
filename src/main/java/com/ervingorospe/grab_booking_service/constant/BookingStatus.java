package com.ervingorospe.grab_booking_service.constant;

public enum BookingStatus {
    PENDING,       // Customer created a booking, waiting for driver
    DRIVER_FOUND,  // A driver accepted the booking
    DRIVER_ARRIVED,// Driver has arrived at the pickup location
    IN_PROGRESS,   // Ride is in progress (customer onboard)
    COMPLETED,     // Ride is completed and payment is done
    CANCELED,      // Booking was canceled by the customer or driver
    NO_SHOW
}
