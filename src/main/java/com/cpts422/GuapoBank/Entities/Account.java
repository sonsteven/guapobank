package com.cpts422.GuapoBank.Entities;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountType;
    private Double balance;
    private boolean frozen;
    private Double interestRate;
    private int dailyTransactionLimit;
    private Double minimumBalance;

    private boolean overdraftOptIn;
    private Double overdraftFee;

    // User can have many Accounts, but each Account can only belong to one User.
    @ManyToOne
    private User user;

    // blank constructor for JPA
    public Account() {
        this.frozen = false;
        this.interestRate = 0.0;
        this.minimumBalance = 50.00;
        this.overdraftOptIn = false;
        this.overdraftFee = 0.0;
    }

    public Account(String accountType, Double balance) {
        this.accountType = accountType;
        this.balance = balance;
        this.frozen = false;

        if (this.accountType.equals("savings")) {
            this.interestRate = 0.02;
            this.minimumBalance = 100.00;
        }
        else {
            this.interestRate = 0.01;
            this.minimumBalance = 50.00;
            this.overdraftFee = 25.00;
        }

        // TODO: set daily limit depending on vip, corporate
        this.dailyTransactionLimit = 5;
        this.overdraftOptIn = false;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public boolean isOverdraftOptIn() {
        return overdraftOptIn;
    }

    public void setOverdraftOptIn(boolean overdraftOptIn) {
        this.overdraftOptIn = overdraftOptIn;
    }

    public Double getOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(Double overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public int getDailyTransactionLimit() {
        return dailyTransactionLimit;
    }
}
