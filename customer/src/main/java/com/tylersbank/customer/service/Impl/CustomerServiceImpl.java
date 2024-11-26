package com.tylersbank.customer.service.Impl;

import com.tylersbank.customer.domain.Customer;
import com.tylersbank.customer.dto.CustomerDto;
import com.tylersbank.customer.mapper.AddressMapper;
import com.tylersbank.customer.mapper.CustomerMapper;
import com.tylersbank.customer.repository.CustomerRepository;
import com.tylersbank.customer.service.AddressService;
import com.tylersbank.customer.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    @Transactional
    @Override
    public CustomerDto create(CustomerDto customerDto) {
        checkIfEmailExist(customerDto.getEmail());

        Customer customer = CustomerMapper.mapToCustomer(customerDto);
//        String hashPswd = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(customer.getPassword());

        String addressId = addressService.saveAddress(customerDto.getAddress());
        customer.setAddressId(addressId);

//        List<String> roles = customer.getRoles().stream()
//                .map(Role::getName)
//                .collect(Collectors.toList());
//
//        Set<Role> dbRoles = roleRepository.findByNameIn(roles);
//
//        customer.setRoles(dbRoles);
        customerRepository.save(customer);
        customerDto.setCustomerId(customer.getCustomerId());

        return customerDto;
    }

    private void checkIfEmailExist(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            throw new RuntimeException("Email already exists: " + email);
        }
    }

    @Override
    public Optional<CustomerDto> findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found: " + email));

        return Optional.of(CustomerMapper.mapToCustomerDto(customer));
    }

    @Override
    public Optional<CustomerDto> findByCustomerId(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer Id not found: " + customerId));

        return Optional.of(CustomerMapper.mapToCustomerDto(customer));
    }
}
