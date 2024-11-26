package com.tylersbank.customer.controller;

import com.tylersbank.customer.domain.Customer;
import com.tylersbank.customer.dto.CustomerDto;
import com.tylersbank.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = customerService.create(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<Optional<CustomerDto>> getByEmail(@RequestParam String email) {
        Optional<CustomerDto> savedCustomer = customerService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(savedCustomer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Optional<CustomerDto>> getCustomerId(@PathVariable String customerId) {
        Optional<CustomerDto> savedCustomer = customerService.findByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(savedCustomer);
    }

}