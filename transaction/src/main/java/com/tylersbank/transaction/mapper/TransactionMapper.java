package com.tylersbank.transaction.mapper;

import com.tylersbank.transaction.domain.Transaction;
import com.tylersbank.transaction.dto.AccountDto;
import com.tylersbank.transaction.dto.TransactionDetailsDto;
import com.tylersbank.transaction.enums.TransactionType;

public class TransactionMapper {

    public static Transaction mapToTransaction(TransactionDetailsDto transactionDetailsDto, AccountDto accountDto) {
        return Transaction.builder()
                .accountId(accountDto.getAccountId())
                .transactionType(transactionDetailsDto.getTransactionType())
                .currency(transactionDetailsDto.getCurrency())
                .amount(transactionDetailsDto.getAmount()).build();
    }

    public static TransactionDetailsDto mapToTransactionDto(Transaction transaction) {
        return TransactionDetailsDto.builder()
                .accountId(transaction.getAccountId())
                .transactionType(transaction.getTransactionType())
                .currency(transaction.getCurrency())
                .amount(transaction.getAmount()).build();
    }
}
