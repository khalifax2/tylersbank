package com.tylersbank.transaction.service.client;

import com.tylersbank.transaction.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account", url = "http://localhost:7010", fallback = AccountFeignClientFallback.class)
public interface AccountFeignClient {

    @GetMapping(value = "/account/getAccount", consumes = "application/json")
    AccountDto getAccount(@RequestParam String accountNo);

    @PutMapping(value = "/account/updateBalance", consumes = "application/json")
    AccountDto updateBalance(@RequestBody AccountDto accountDto);
}
