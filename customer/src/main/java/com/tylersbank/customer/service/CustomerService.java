package com.tylersbank.customer.service;

import com.tylersbank.customer.domain.Customer;
import com.tylersbank.customer.dto.CustomerDto;

import java.util.Optional;

public interface CustomerService {
    CustomerDto create(CustomerDto customerDto);
    Optional<CustomerDto> findByEmail(String email);
    Optional<CustomerDto> findByCustomerId(String customerId);
}
