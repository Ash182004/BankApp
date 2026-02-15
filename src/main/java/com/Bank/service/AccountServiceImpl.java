package com.Bank.service;

import com.Bank.entity.Account;
import com.Bank.exception.AccountNotFoundException;
import com.Bank.exception.BusinessException;
import com.Bank.repo.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepo;

    public AccountServiceImpl(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Account createAccount(String holderName, BigDecimal initialBalance) {

        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Initial balance cannot be negative");
        }

        String accountNumber = UUID.randomUUID().toString();

        Account account = new Account(
                accountNumber,
                holderName,
                initialBalance
        );

        return accountRepo.save(account);
    }

    @Override
    public Account getByAccountNumber(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }
}
