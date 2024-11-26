package com.tylersbank.account.service.Impl;

import com.tylersbank.account.domain.Account;
import com.tylersbank.account.dto.AccountDto;
import com.tylersbank.account.dto.CustomerDto;
import com.tylersbank.account.enums.AccountStatusEnum;
import com.tylersbank.account.exceptions.AccountAlreadyExistException;
import com.tylersbank.account.exceptions.AccountNotExistException;
import com.tylersbank.account.exceptions.CustomerNotFoundException;
import com.tylersbank.account.exceptions.ExternalServiceException;
import com.tylersbank.account.mapper.AccountMapper;
import com.tylersbank.account.repository.AccountsRepository;
import com.tylersbank.account.service.AccountService;
import com.tylersbank.account.service.client.CustomerFeignClient;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountsRepository accountsRepository;
    private final CustomerFeignClient customerFeignClient;


    @Transactional
    @Override
    public AccountDto create(AccountDto accountDto) {
        try {
            Optional<CustomerDto> resCustomerDto = customerFeignClient.findByEmail(accountDto.getEmail());
            if (resCustomerDto.isEmpty()) {
                throw new CustomerNotFoundException("Customer not found");
            }

            Optional<Account> existingAccount = accountsRepository.findByCustomerIdAndAccountType(resCustomerDto.get().getCustomerId(), accountDto.getAccountType());
            if (existingAccount.isPresent()) {
                throw new AccountAlreadyExistException("Account already exists");
            }

            accountDto.setStatus(AccountStatusEnum.ACTIVE);
            Random random = new Random();
            accountDto.setAccountNo(String.valueOf(random.nextInt(1000)));

            Account newAccount = AccountMapper.mapToAccount(accountDto);
            newAccount.setCustomerId(resCustomerDto.get().getCustomerId());


            accountsRepository.save(newAccount);
            accountDto.setAccountId(newAccount.getAccountId());

            return accountDto;
        } catch (FeignException fe) {
            throw new ExternalServiceException("Error calling external service", fe.getCause());
        }
    }

    private Account validateAccountIfExist(String accountNo) {
        return accountsRepository.findByAccountNo(accountNo)
                .orElseThrow(() -> new AccountNotExistException("Account doesn't exists"));
    }



    @Override
    public AccountDto getAccount(String accountNo) {
        Account account = validateAccountIfExist(accountNo);
        AccountDto accountDto = AccountMapper.mapToAccountDto(account);

        Optional<CustomerDto> resCustomerDto = customerFeignClient.findByCustomerId(account.getCustomerId());
        accountDto.setEmail(resCustomerDto.get().getEmail());

        return accountDto;
    }

    @Override
    public AccountDto updateBalance(AccountDto accountDto) {
        accountsRepository.updateBalance(accountDto.getAccountId(), accountDto.getBalance());
        return accountDto;
    }

//    @Override
//    public List<AccountDto> getAllAccount() {
//        List<Account> accounts = accountsRepository.findAll();
//        List<AccountDto> accountDtos = new ArrayList<>();
//        accounts.forEach(account -> {
//            AccountDto accountDto = AccountMapper.mapToAccountDto(account);
//            accountDto.setEmail(account.getCustomer().getEmail());
//            accountDtos.add(accountDto);
//        });
//
//        return accountDtos;
//    }
}
