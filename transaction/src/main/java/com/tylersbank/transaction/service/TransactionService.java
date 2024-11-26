package com.tylersbank.transaction.service;

import com.tylersbank.transaction.dto.TransactionDetailsDto;

import java.util.List;

public interface TransactionService {
    void withdrawal(TransactionDetailsDto transactionDetailsDto);
    void deposit(TransactionDetailsDto transactionDetailsDto);
    List<TransactionDetailsDto> getAllTransactions();
    List<TransactionDetailsDto> getTransaction(String accountNo);
}
