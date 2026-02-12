package com.Bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String transactionId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private TransactionType type;

    @Column(nullable = false, precision = 15, scale = 2, updatable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false, updatable = false)
    private Long performedBy;

    private Long approvedBy;

    private LocalDateTime approvedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Transaction() {}

    public Transaction(Account account,
                       TransactionType type,
                       BigDecimal amount,
                       Long performedBy,
                       TransactionStatus status) {

        this.transactionId = UUID.randomUUID().toString();
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.performedBy = performedBy;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public void approve(Long managerId) {
        this.status = TransactionStatus.COMPLETED;
        this.approvedBy = managerId;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(Long managerId) {
        this.status = TransactionStatus.REJECTED;
        this.approvedBy = managerId;
        this.approvedAt = LocalDateTime.now();
    }
}
