package com.Bank.service;

import com.Bank.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionHistoryService {

    Page<Transaction> getAccountTransactions(
            String accountNumber,
            Pageable pageable
    );
}
