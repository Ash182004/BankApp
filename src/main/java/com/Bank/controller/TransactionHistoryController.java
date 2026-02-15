package com.Bank.controller;

import com.Bank.entity.Transaction;
import com.Bank.service.TransactionHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transactions/history")
public class TransactionHistoryController {

    private final TransactionHistoryService historyService;

    public TransactionHistoryController(
            TransactionHistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * MANAGER → can view history of ANY account
     * CLERK   → can view permitted histories (enforced by role)
     */
    @PreAuthorize("hasAnyRole('MANAGER','CLERK')")
    @GetMapping("/{accountNumber}")
    public Page<Transaction> getTransactionHistory(
            @PathVariable String accountNumber,
            Pageable pageable) {

        return historyService.getAccountTransactions(
                accountNumber,
                pageable
        );
    }
}
