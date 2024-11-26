package com.tylersbank.account.dto;

import com.tylersbank.account.enums.AccountStatusEnum;
import com.tylersbank.account.enums.AccountTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Enter a valid email")
    private String email;

    private String accountId;
    private String accountNo;
    private AccountTypeEnum accountType;
    private AccountStatusEnum status;
    private String currency;
    private BigInteger balance;
//    private TransactionDto transaction;

}
