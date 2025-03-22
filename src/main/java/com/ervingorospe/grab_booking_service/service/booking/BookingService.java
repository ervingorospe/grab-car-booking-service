package com.ervingorospe.grab_booking_service.service.booking;

import com.ervingorospe.grab_booking_service.model.DTO.BookingDTO;
import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.DTO.CustomerDTO;
import com.ervingorospe.grab_booking_service.model.entity.Booking;

public interface BookingService {
    BookingDTO save(BookingRequestDTO bookingRequestDTO);
    BookingDTO cancelBooking(String id);
    BookingDTO acceptBooking(CustomerDTO customerDTO, String id);
}
