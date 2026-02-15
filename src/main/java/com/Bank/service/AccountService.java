package com.Bank.service;

import com.Bank.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account createAccount(String holderName, BigDecimal initialBalance);

    Account getByAccountNumber(String accountNumber);

    List<Account> getAllAccounts();
}
