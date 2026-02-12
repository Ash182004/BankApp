package com.Bank.dto;

import java.math.BigDecimal;

import com.Bank.entity.Transaction;

import lombok.Getter;

@Getter
public class PendingApprovalDto {

    private String transactionId;
    private String type;
    private BigDecimal amount;
    private String accountNumber;

    public PendingApprovalDto(Transaction tx) {
        this.transactionId = tx.getTransactionId();
        this.type = tx.getType().name();
        this.amount = tx.getAmount();
        this.accountNumber = tx.getAccount().getAccountNumber();
    }
}

