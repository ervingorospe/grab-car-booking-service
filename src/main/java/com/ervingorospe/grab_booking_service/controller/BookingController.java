package com.ervingorospe.grab_booking_service.controller;

import com.ervingorospe.grab_booking_service.model.DTO.BookingDTO;
import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.DTO.CustomerDTO;
import com.ervingorospe.grab_booking_service.model.entity.Booking;
import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.service.booking.BookingService;
import com.ervingorospe.grab_booking_service.service.customer.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;
    private final CustomerService customerService;

    @Autowired
    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Booking Service";
    }

    @PostMapping("/second-test")
    public String testAgainRoute() {
        return "Booking Service Again";
    }

    @PostMapping("/create/test")
    public ResponseEntity<Customer> testSaveBooking(@RequestBody @Valid BookingRequestDTO bookingRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(bookingRequestDTO.customer()));
    }

    @PostMapping("/create")
    public ResponseEntity<BookingDTO> saveBooking(@RequestBody @Valid BookingRequestDTO bookingRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.save(bookingRequestDTO));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.cancelBooking(id));
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<BookingDTO> acceptBooking(@RequestBody @Valid CustomerDTO customerDTO, @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.acceptBooking(customerDTO, id));
    }

    @PutMapping("/driver-arrived/{id}")
    public ResponseEntity<BookingDTO> driverArrived(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.driverArrived(id));
    }

    @PutMapping("/pickup/{id}")
    public ResponseEntity<BookingDTO> pickupBooking(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.pickupBooking(id));
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<BookingDTO> completeBooking(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.arrivedAtDestination(id));
    }
}
