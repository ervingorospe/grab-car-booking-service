package com.ervingorospe.grab_booking_service.service.booking;

import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.entity.Booking;

public interface BookingService {
    Booking save(BookingRequestDTO bookingRequestDTO);
    Booking cancelBooking(String id);
}
