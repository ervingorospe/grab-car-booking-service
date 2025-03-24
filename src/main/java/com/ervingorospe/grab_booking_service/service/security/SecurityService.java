package com.ervingorospe.grab_booking_service.service.security;

import com.ervingorospe.grab_booking_service.handler.error.CustomAccessDeniedException;
import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.DTO.CustomerDTO;
import com.ervingorospe.grab_booking_service.model.entity.Booking;
import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.service.booking.BookingService;
import com.ervingorospe.grab_booking_service.service.booking.BookingServiceImpl;
import com.ervingorospe.grab_booking_service.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final CustomerService customerService;
    private final BookingServiceImpl bookingService;

    @Autowired
    public SecurityService(CustomerService customerService, BookingServiceImpl bookingService) {
        this.customerService = customerService;
        this.bookingService = bookingService;
    }

    public boolean isOwnerOfAccount(BookingRequestDTO bookingRequestDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof String email) || !email.equals(bookingRequestDTO.customer().email())) {
            throw new AccessDeniedException("Unauthorized");
        }

        return true;
    }

    public boolean isOwnerOfAccount(String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Booking booking = bookingService.findBookingById(id);

        if (!(principal instanceof String email) || !email.equals(booking.getCustomer().getEmail())) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }

    public boolean isOwnerOfAccount(CustomerDTO customerDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof String email) || !email.equals(customerDTO.email())) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }

    public boolean isClient(String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Booking booking = bookingService.findBookingById(id);

        if (!(principal instanceof String email) || !booking.getCustomer().getRole().equals("CLIENT")) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }

    public boolean isDriver(CustomerDTO customerDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof String email) || !customerDTO.role().equals("DRIVER")) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }

    public boolean isTheDriver(String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Booking booking = bookingService.findBookingById(id);

        if (!(principal instanceof String email) || !email.equals(booking.getDriver().getEmail())) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }
}
