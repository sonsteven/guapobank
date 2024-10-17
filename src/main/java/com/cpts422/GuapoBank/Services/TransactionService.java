package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;

public interface TransactionService {

    public Iterable<Transaction> findAll();

    public Transaction save(Transaction transaction);

    public Iterable<Transaction> findBySenderAccount(Account account);

    public Iterable<Transaction> findByRecipientAccount(Account account);

    public Double calculateTransferFee(Account sender, Double amount);

    public boolean isOverDailyTransactionLimit(Account account);

    public void createTransaction(Account sender, Account recipient, Transaction transaction) throws Exception;
}
