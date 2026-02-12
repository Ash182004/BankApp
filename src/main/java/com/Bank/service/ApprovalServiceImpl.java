package com.Bank.service;

import com.Bank.dto.PendingApprovalDto;
import com.Bank.entity.Transaction;
import com.Bank.entity.TransactionStatus;
import com.Bank.exception.BusinessException;
import com.Bank.exception.TransactionNotFoundException;

import com.Bank.repo.TransactionRepository;
import com.Bank.security.CustomUserDetails;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApprovalServiceImpl implements ApprovalService {

    private final TransactionRepository transactionRepo;

    public ApprovalServiceImpl(TransactionRepository transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PendingApprovalDto> getPendingWithdrawals() {

        return transactionRepo
                .findPendingWithAccount(TransactionStatus.PENDING_APPROVAL)
                .stream()
                .map(PendingApprovalDto::new)
                .toList();
    }

    @Override
    public void approve(String transactionId) {
        Transaction tx = transactionRepo.findByTransactionId(transactionId);
        if (tx == null) {
            throw new TransactionNotFoundException("Transaction not found: " + transactionId);
        }

        if (tx.getStatus() != TransactionStatus.PENDING_APPROVAL) {
            throw new BusinessException("Transaction is not pending approval");
        }

        Long managerId = getCurrentUserId();
        tx.approve(managerId);
        tx.getAccount().debit(tx.getAmount());
    }

    @Override
    public void reject(String transactionId) {
        Transaction tx = transactionRepo.findByTransactionId(transactionId);
        if (tx == null) {
            throw new TransactionNotFoundException("Transaction not found: " + transactionId);
        }

        if (tx.getStatus() != TransactionStatus.PENDING_APPROVAL) {
            throw new BusinessException("Transaction is not pending approval");
        }

        Long managerId = getCurrentUserId();
        tx.reject(managerId);
    }

    private Long getCurrentUserId() {
        return ((CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getId();
    }
}
