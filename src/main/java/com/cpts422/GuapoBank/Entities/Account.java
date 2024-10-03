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

    // User can have many Accounts, but each Account can only belong to one User.
    @ManyToOne
    private User user;

    // blank constructor for JPA
    public Account() {
        this.frozen = false;
    }

    public Account(String accountType, Double balance) {
        this.accountType = accountType;
        this.balance = balance;
        this.frozen = false;
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
}
