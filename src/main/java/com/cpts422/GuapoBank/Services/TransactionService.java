package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Transaction;

public interface TransactionService {

    public Iterable<Transaction> findAll();

    public Transaction save(Transaction transaction);
}
