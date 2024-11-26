package com.tylersbank.account.service.client;

import com.tylersbank.account.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "customer", url="http://localhost:7000", fallback = CustomerFallback.class)
public interface CustomerFeignClient {

    @GetMapping(value = "/customer/getByEmail", consumes = "application/json")
    Optional<CustomerDto> findByEmail(@RequestParam String email);

    @GetMapping(value = "/customer/{customerId}", consumes = "application/json")
    Optional<CustomerDto> findByCustomerId(@PathVariable String customerId);
}
