package com.ervingorospe.grab_booking_service.handler.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingCancellationException extends RuntimeException{
    public BookingCancellationException(String message) {
        super(message);
    }
}
