package com.tylersbank.account.service.client;

import com.tylersbank.account.dto.CustomerDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerFallback implements CustomerFeignClient{

    @Override
    public Optional<CustomerDto> findByEmail(String email) {
        throw new RuntimeException("Something went wrong finding email.");
    }

    @Override
    public Optional<CustomerDto> findByCustomerId(String customerId) {
        return Optional.empty();
    }
}
