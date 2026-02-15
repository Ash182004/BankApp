package com.Bank.controller;

import com.Bank.dto.ApiResponse;
import com.Bank.dto.DepositRequestDto;
import com.Bank.dto.WithdrawRequestDto;
import com.Bank.entity.TransactionStatus;
import com.Bank.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasRole('CLERK')")
    @RequestMapping(
            value = "/deposit",
            method = {RequestMethod.POST, RequestMethod.PUT}
    )
    public ApiResponse deposit(@Valid @RequestBody DepositRequestDto dto) {

        transactionService.deposit(
                dto.getAccountNumber(),
                dto.getAmount()
        );

        return new ApiResponse("Deposit successful");
    }

    @PreAuthorize("hasRole('CLERK')")
    @PostMapping("/withdraw")
    public ApiResponse withdraw(@Valid @RequestBody WithdrawRequestDto dto) {

        TransactionStatus status = transactionService.withdraw(
                dto.getAccountNumber(),
                dto.getAmount()
        );

        if (status == TransactionStatus.PENDING_APPROVAL) {
            return new ApiResponse("Withdrawal request submitted for approval");
        }

        return new ApiResponse("Withdrawal successful");
    }
}