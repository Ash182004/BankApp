package com.Bank.service;

import com.Bank.dto.PendingApprovalDto;
import com.Bank.entity.Transaction;

import java.util.List;

public interface ApprovalService {

    List<PendingApprovalDto> getPendingWithdrawals();

    void approve(String transactionId);

    void reject(String transactionId);
}
