package com.tylersbank.account.service;



import com.tylersbank.account.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto create(AccountDto accountDto);
    AccountDto getAccount(String accountNo);
    AccountDto updateBalance(AccountDto accountDto);
//    List<AccountDto> getAllAccount();
}
