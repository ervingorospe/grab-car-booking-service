package com.ervingorospe.grab_booking_service.service.security;

import com.ervingorospe.grab_booking_service.handler.error.CustomAccessDeniedException;
import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.DTO.CustomerDTO;
import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final CustomerService customerService;

    @Autowired
    public SecurityService(CustomerService customerService) {
        this.customerService = customerService;
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
        Customer customer = customerService.getCustomerById(id);

        if (!(principal instanceof String email) || !email.equals(customer.getEmail())) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }

    public boolean isOwnerOfAccount(CustomerDTO customerDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(customerDTO.id());
        System.out.println(customer);
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

    public boolean isDriver(CustomerDTO customerDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof String email) || !customerDTO.role().equals("DRIVER")) {
            throw new CustomAccessDeniedException("Unauthorized");
        }

        return true;
    }
}
