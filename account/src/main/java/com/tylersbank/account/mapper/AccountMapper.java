package com.tylersbank.account.mapper;

import com.tylersbank.account.domain.Account;
import com.tylersbank.account.dto.AccountDto;


public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
        return Account.builder()
                .accountNo(accountDto.getAccountNo())
                .status(accountDto.getStatus())
                .accountType(accountDto.getAccountType())
                .currency(accountDto.getCurrency())
                .build();
    }

    public static AccountDto mapToAccountDto(Account account) {
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .accountNo(account.getAccountNo())
                .status(account.getStatus())
                .accountType(account.getAccountType())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .build();
    }
}
