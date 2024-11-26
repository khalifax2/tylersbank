package com.tylersbank.transaction.dto;

import com.tylersbank.transaction.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class TransactionDetailsDto {

    private TransactionType transactionType;
    private String accountId;
    private String accountNo;
    private BigInteger amount;
    private String currency;
    private String description;

}
