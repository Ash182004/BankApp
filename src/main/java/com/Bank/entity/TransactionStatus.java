package com.Bank.entity;

public enum TransactionStatus {
    COMPLETED,          // deposit OR approved withdrawal
    PENDING_APPROVAL,   // waiting for manager
    REJECTED            // rejected by manager
}
