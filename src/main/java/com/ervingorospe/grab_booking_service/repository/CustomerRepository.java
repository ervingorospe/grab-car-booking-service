package com.ervingorospe.grab_booking_service.repository;

import com.ervingorospe.grab_booking_service.model.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends MongoRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
}
