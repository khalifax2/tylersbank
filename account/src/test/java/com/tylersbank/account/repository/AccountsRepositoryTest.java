package com.tylersbank.account.repository;

import com.tylersbank.account.domain.Account;
import com.tylersbank.account.enums.AccountStatusEnum;
import com.tylersbank.account.enums.AccountTypeEnum;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountsRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AccountsRepository accountsRepository;

    private Account account1;

    private String accountId;
    private String customerId;

    @BeforeEach
    void setup() {
        customerId = UUID.randomUUID().toString();
        account1 = Account.builder()
                .accountNo("001")
                .customerId(customerId)
                .accountType(AccountTypeEnum.SAVINGS)
                .balance(BigInteger.ZERO)
                .currency("PHP")
                .status(AccountStatusEnum.ACTIVE).build();

        testEntityManager.persistAndFlush(account1);
        accountId = account1.getAccountId();
    }

    @Test
    void findByAccountNo() {
        Optional<Account> account = accountsRepository.findByAccountNo(account1.getAccountNo());
        assertThat(account).isNotEmpty();
        assertThat(account.get().getAccountId()).isEqualTo(account1.getAccountId());
    }

    @Test
    void findByCustomerIdAndAccountType() {
        Optional<Account> account = accountsRepository.findByCustomerIdAndAccountType(customerId, AccountTypeEnum.SAVINGS);
        assertThat(account).isNotEmpty();
        assertThat(account.get().getAccountId()).isEqualTo(account1.getAccountId());
        assertThat(account.get().getAccountType()).isEqualTo(account1.getAccountType());
    }

    @Test
    void updateBalance() {
        BigInteger newBalance =  BigInteger.valueOf(1000);
        accountsRepository.updateBalance(accountId, newBalance);
        testEntityManager.clear(); // Clears the persistence context to ensure the update is reflected

        Optional<Account> updatedAccount = accountsRepository.findById(account1.getAccountId());
        assertThat(updatedAccount).isPresent();
        assertThat(updatedAccount.get().getBalance()).isEqualTo(newBalance);
    }
}