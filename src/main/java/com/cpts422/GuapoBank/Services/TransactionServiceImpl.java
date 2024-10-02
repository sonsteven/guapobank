package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Repositories.TransactionRepository;

public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
