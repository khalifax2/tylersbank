package com.tylersbank.account.domain;

import com.tylersbank.account.enums.AccountStatusEnum;
import com.tylersbank.account.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "customerId")
    private String customerId;

    @Column(name = "account_type")
    private AccountTypeEnum accountType;

    @Column(name = "balance")
    private BigInteger balance;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private AccountStatusEnum status;

}
