package com.ervingorospe.grab_booking_service.handler.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingAssignmentException extends RuntimeException {
    public BookingAssignmentException(String message) {
        super(message);
    }
}
