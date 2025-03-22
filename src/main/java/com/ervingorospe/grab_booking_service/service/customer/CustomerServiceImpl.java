package com.ervingorospe.grab_booking_service.service.customer;

import com.ervingorospe.grab_booking_service.handler.error.UserNotFoundException;
import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
