package com.ervingorospe.grab_booking_service.service.security;

import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public boolean isOwnerOfAccount(BookingRequestDTO bookingRequestDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof String email) || !email.equals(bookingRequestDTO.customer().email())) {
            throw new AccessDeniedException("Unauthorized");
        }

        return true;
    }
}
