package com.Bank.service;

import com.Bank.entity.Transaction;
import com.Bank.exception.AccountNotFoundException;
import com.Bank.repo.AccountRepository;
import com.Bank.repo.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionRepository transactionRepo;
    private final AccountRepository accountRepo;

    public TransactionHistoryServiceImpl(TransactionRepository transactionRepo,
                                         AccountRepository accountRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    @Override
    public Page<Transaction> getAccountTransactions(
            String accountNumber,
            Pageable pageable) {

        // Validate account exists (audit safety)
        accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found: " + accountNumber));

        return transactionRepo.findByAccountAccountNumber(
                accountNumber,
                pageable
        );
    }
}
