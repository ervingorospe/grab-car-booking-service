package com.ervingorospe.grab_booking_service.controller;

import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.entity.Booking;
import com.ervingorospe.grab_booking_service.service.booking.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Booking Service";
    }

    @PostMapping("/second-test")
    public String testAgainRoute() {
        return "Booking Service Again";
    }

    @PostMapping("/create")
    public Booking saveBooking(@RequestBody @Valid BookingRequestDTO bookingRequestDTO) {
        return bookingService.save(bookingRequestDTO);
    }
}
