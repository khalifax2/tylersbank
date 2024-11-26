package com.tylersbank.customer.mapper;

import com.tylersbank.customer.domain.Customer;
import com.tylersbank.customer.dto.CustomerDto;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .mobileNumber(customerDto.getMobileNumber())
//                .roles(customerDto.getRoles())
                .gender(customerDto.getGender())
                .dateOfBirth(customerDto.getDateOfBirth())
                .build();
    }

    public static CustomerDto mapToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .mobileNumber(customer.getMobileNumber())
//                .roles(customer.getRoles())
                .gender(customer.getGender())
                .dateOfBirth(customer.getDateOfBirth())
                .build();
    }
}
