package com.Bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false, updatable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String holderName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Version
    private Long version; // optimistic locking

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Account() {}

    public Account(String accountNumber, String holderName, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void debit(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}
