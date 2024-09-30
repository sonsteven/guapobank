package com.cpts422.GuapoBank.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private final LocalDateTime transactionDate;

    @ManyToOne
    private Account senderAccount;

    @ManyToOne
    private Account recipientAccount;

    public Transaction(Double amount, Account senderAccount, Account recipientAccount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(Account recipientAccount) {
        this.recipientAccount = recipientAccount;
    }
}
