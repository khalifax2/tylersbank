package com.tylersbank.transaction.dto;

import com.tylersbank.transaction.enums.AccountStatusEnum;
import com.tylersbank.transaction.enums.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountDto {

    private String email;
    private String accountId;
    private String accountNo;
    private AccountTypeEnum accountType;
    private AccountStatusEnum status;
    private String currency;
    private BigInteger balance;
//    private TransactionDto transaction;

}
