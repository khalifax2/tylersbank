package com.tylersbank.transaction.service.Impl;

import com.tylersbank.transaction.domain.Transaction;
import com.tylersbank.transaction.dto.AccountDto;
import com.tylersbank.transaction.dto.TransactionDetailsDto;
import com.tylersbank.transaction.enums.TransactionType;
import com.tylersbank.transaction.mapper.TransactionMapper;
import com.tylersbank.transaction.repository.TransactionRepository;
import com.tylersbank.transaction.service.TransactionService;
import com.tylersbank.transaction.service.client.AccountFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountFeignClient accountFeignClient;

    @Transactional
    @Override
    public void withdrawal(TransactionDetailsDto transactionDetailsDto) {
        AccountDto accountDto = accountFeignClient.getAccount(transactionDetailsDto.getAccountNo());
        validateBalance(accountDto.getBalance(), transactionDetailsDto.getAmount());

        transactionDetailsDto.setTransactionType(TransactionType.WITHDRAWAL);
        Transaction newTransaction = TransactionMapper.mapToTransaction(transactionDetailsDto, accountDto);
        transactionRepository.save(newTransaction);

        BigInteger availableBalance = BigInteger.ZERO;
        if (accountDto.getBalance() != null) {
            availableBalance = accountDto.getBalance().subtract(transactionDetailsDto.getAmount());
        }
        accountDto.setBalance(availableBalance);
        accountFeignClient.updateBalance(accountDto);
    }

    @Transactional
    @Override
    public void deposit(TransactionDetailsDto transactionDetailsDto) {
        AccountDto accountDto = accountFeignClient.getAccount(transactionDetailsDto.getAccountNo());

        transactionDetailsDto.setTransactionType(TransactionType.DEPOSIT);
        Transaction newTransaction = TransactionMapper.mapToTransaction(transactionDetailsDto, accountDto);
        transactionRepository.save(newTransaction);

        BigInteger availableBalance = transactionDetailsDto.getAmount();
        if (accountDto.getBalance() != null) {
            availableBalance = transactionDetailsDto.getAmount().add(accountDto.getBalance());
        }
        accountDto.setBalance(availableBalance);
        accountFeignClient.updateBalance(accountDto);
    }

    public void validateBalance(BigInteger balance, BigInteger amount) {
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance." + balance);
        }
    }

    @Override
    public List<TransactionDetailsDto> getAllTransactions() {
        List<Transaction> transactionList = transactionRepository.findAll();

        return transactionList.stream()
                .map(TransactionMapper::mapToTransactionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailsDto> getTransaction(String accountNo) {
        AccountDto accountDto = accountFeignClient.getAccount(accountNo);
        List<Transaction> transactionList = transactionRepository.findByAccountId(accountDto.getAccountId());

        return transactionList.stream()
                .map(transaction -> {
                    TransactionDetailsDto transactionDetailsDto = TransactionMapper.mapToTransactionDto(transaction);
                    transactionDetailsDto.setAccountNo(accountNo);
                    return transactionDetailsDto;
                })
                .collect(Collectors.toList());
    }
}
