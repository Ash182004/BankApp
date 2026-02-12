package com.Bank.service;

import com.Bank.entity.*;
import com.Bank.exception.AccountNotFoundException;
import com.Bank.exception.BusinessException;
import com.Bank.exception.InsufficientBalanceException;
import com.Bank.repo.AccountRepository;
import com.Bank.repo.TransactionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private static final BigDecimal APPROVAL_LIMIT = new BigDecimal("200000");

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    public TransactionServiceImpl(AccountRepository accountRepo,
                                  TransactionRepository transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public void deposit(String accountNumber, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Deposit amount must be greater than zero");
        }

        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));

        Long clerkId = getCurrentUserId();

        TransactionStatus status =
                amount.compareTo(APPROVAL_LIMIT) <= 0
                        ? TransactionStatus.COMPLETED
                        : TransactionStatus.PENDING_APPROVAL;

        Transaction tx = new Transaction(
                account,
                TransactionType.DEPOSIT,
                amount,
                clerkId,
                status
        );

        if (status == TransactionStatus.COMPLETED) {
            account.credit(amount); // ✅ credit only if approved
        }

        transactionRepo.save(tx);
    }

    @Override
    public TransactionStatus withdraw(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Withdrawal amount must be greater than zero");
        }

        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        Long clerkId = getCurrentUserId();

        TransactionStatus status =
                amount.compareTo(APPROVAL_LIMIT) <= 0
                        ? TransactionStatus.COMPLETED
                        : TransactionStatus.PENDING_APPROVAL;

        Transaction tx = new Transaction(
                account,
                TransactionType.WITHDRAWAL,
                amount,
                clerkId,
                status
        );

        if (status == TransactionStatus.COMPLETED) {
            account.debit(amount);
        }

        transactionRepo.save(tx);

        return status;
    }
    private Long getCurrentUserId() {
        return (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
