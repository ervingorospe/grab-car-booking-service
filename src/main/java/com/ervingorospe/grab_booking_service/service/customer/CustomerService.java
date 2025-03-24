package com.ervingorospe.grab_booking_service.service.customer;

import com.ervingorospe.grab_booking_service.model.DTO.CustomerDTO;
import com.ervingorospe.grab_booking_service.model.entity.Customer;

public interface CustomerService {
    Customer save(CustomerDTO customerDTO);
    Customer getCustomerById(String id);
}
