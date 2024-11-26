package com.tylersbank.account.repository;

import com.tylersbank.account.domain.Account;
import com.tylersbank.account.enums.AccountTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, String> {
    Optional<Account> findByAccountNo(String accountNo);
    Optional<Account> findByCustomerIdAndAccountType(String customerId, AccountTypeEnum accountType);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = :balance WHERE a.accountId = :accountId")
    void updateBalance(@Param("accountId") String accountId, @Param("balance") BigInteger balance);
}
