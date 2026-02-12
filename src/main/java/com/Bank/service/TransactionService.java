package com.Bank.service;

import java.math.BigDecimal;

import com.Bank.entity.TransactionStatus;

public interface TransactionService {

    void deposit(String accountNumber, BigDecimal amount);

    TransactionStatus withdraw(String accountNumber, BigDecimal amount);
}

