package com.ervingorospe.grab_booking_service.repository;

import com.ervingorospe.grab_booking_service.model.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BookingRepository extends MongoRepository<Booking, UUID> {
}
