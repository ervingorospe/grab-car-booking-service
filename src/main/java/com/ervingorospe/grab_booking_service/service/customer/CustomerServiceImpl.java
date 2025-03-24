package com.ervingorospe.grab_booking_service.service.customer;

import com.ervingorospe.grab_booking_service.handler.error.UserNotFoundException;
import com.ervingorospe.grab_booking_service.model.DTO.CustomerDTO;
import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerDTO.email());
        return existingCustomer.orElseGet(() -> customerRepository.save(new Customer(customerDTO)));
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
