package com.tylersbank.transaction.service.client;

import com.tylersbank.transaction.dto.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountFeignClientFallback implements AccountFeignClient{

    @Override
    public AccountDto getAccount(String accountNo) {
        return null;
    }

    @Override
    public AccountDto updateBalance(AccountDto accountDto) {
        return null;
    }
}
