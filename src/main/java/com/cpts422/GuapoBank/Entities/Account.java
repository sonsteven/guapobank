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

    // User can have many Accounts, but each Account can only belong to one User.
    @ManyToOne
    private User user;

    // blank constructor for JPA
    public Account() {
        this.frozen = false;
        this.interestRate = 0.0;
    }

    public Account(String accountType, Double balance, User user) {
        this.accountType = accountType;
        this.balance = balance;
        this.frozen = false;
        this.user = user;
        if (this.accountType.equals("savings")) {
            this.interestRate = 0.02;
        }
        else {
            this.interestRate = 0.01;
        }

        if (this.user.isCorporate()) {
            this.dailyTransactionLimit = 100;
        }
        else if (this.user.isVip()) {
            this.dailyTransactionLimit = 15;
        }
        else if (this.user.isMilitary()) {
            this.dailyTransactionLimit = 10;
        }
        else {
            this.dailyTransactionLimit = 5;
        }
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

    public int getDailyTransactionLimit() {
        return dailyTransactionLimit;
    }
}
