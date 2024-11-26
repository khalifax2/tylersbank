package com.tylersbank.account.service.Impl;

import com.tylersbank.account.domain.Account;
import com.tylersbank.account.dto.AccountDto;
import com.tylersbank.account.dto.CustomerDto;
import com.tylersbank.account.enums.AccountTypeEnum;
import com.tylersbank.account.enums.GenderEnum;
import com.tylersbank.account.repository.AccountsRepository;
import com.tylersbank.account.service.client.CustomerFeignClient;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private CustomerFeignClient customerFeignClient;

    @InjectMocks
    private AccountServiceImpl accountService;

    private AccountDto requestAccountDto;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        requestAccountDto = AccountDto.builder()
                .email("echo1@gmail.com")
                .accountType(AccountTypeEnum.SAVINGS)
                .currency("PHP").build();

        customerDto = CustomerDto.builder()
                .customerId(UUID.randomUUID().toString())
                .firstName("Jericho")
                .lastName("Castro")
                .email("echo1@gmail.com")
                .mobileNumber("09174646125")
                .gender(GenderEnum.MALE)
                .dateOfBirth(LocalDate.now()).build();
    }

    @Test
    void testCreateUser_whenValidDetails_returnsAccountDetails() {
        // Arrange
        when(customerFeignClient.findByEmail(anyString())).thenReturn(Optional.of(customerDto));
        when(accountsRepository.findByCustomerIdAndAccountType(anyString(), any(AccountTypeEnum.class))).thenReturn(Optional.empty());

        AccountDto createdAccountDto = accountService.create(requestAccountDto);

        assertNotNull(createdAccountDto);
    }
}