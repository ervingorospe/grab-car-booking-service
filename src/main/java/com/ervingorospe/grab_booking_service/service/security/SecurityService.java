package com.ervingorospe.grab_booking_service.service.security;

import com.ervingorospe.grab_booking_service.handler.error.CustomAccessDeniedException;
import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.service.customer.CustomerService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private CustomerService customerService;

    public boolean isOwnerOfAccount(BookingRequestDTO bookingRequestDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof String email) || !email.equals(bookingRequestDTO.customer().email())) {
            throw new AccessDeniedException("Unauthorized");
        }

        return true;
    }

    public boolean isOwnerOfAccount(String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(id);

        if (!(principal instanceof String email) || !email.equals(customer.getEmail())) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }

    public boolean isClient(String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(id);

        if (!(principal instanceof String email) || !customer.getRole().equals("CLIENT")) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }
}
