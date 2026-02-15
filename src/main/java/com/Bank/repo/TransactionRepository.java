package com.Bank.repo;

import com.Bank.entity.Transaction;
import com.Bank.entity.TransactionStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ✅ For Transaction History (pagination)
    Page<Transaction> findByAccountAccountNumber(
            String accountNumber,
            Pageable pageable
    );

    // ✅ For Manager Pending Withdrawals
    @Query("""
        SELECT t FROM Transaction t
        JOIN FETCH t.account
        WHERE t.status = :status
    """)
    List<Transaction> findPendingWithAccount(
            @Param("status") TransactionStatus status
    );

    // ✅ Find by transactionId
    Transaction findByTransactionId(String transactionId);
}
