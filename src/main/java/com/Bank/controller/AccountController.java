package com.Bank.controller;

import com.Bank.dto.AccountCreateRequestDto;
import com.Bank.entity.Account;
import com.Bank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public Account createAccount(
            @Valid @RequestBody AccountCreateRequestDto dto) {

        return accountService.createAccount(
                dto.getHolderName(),
                dto.getInitialBalance()
        );
    }

    @PreAuthorize("hasAnyRole('MANAGER','CLERK')")
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getByAccountNumber(accountNumber);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}
