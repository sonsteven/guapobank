package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
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

    @Override
    public Iterable<Transaction> findBySenderAccount(Account account) {
        return transactionRepository.findBySenderAccount(account);
    }

    @Override
    public Iterable<Transaction> findByRecipientAccount(Account account) {
        return transactionRepository.findByRecipientAccount(account);
    }

    @Override
    public Double calculateTransferFee(Account sender, Double amount) {

        // calculate
        Double transferFee = 0.0d;
        if (amount <= 500) {
            transferFee = amount * 0.05;
        }
        else if (amount <= 1000) {
            transferFee = amount * 0.04;
        }
        else if (amount <= 3000) {
            transferFee = amount * 0.03;
        }
        else if (amount <= 5000) {
            transferFee = amount * 0.025;
        }
        else {
            if (sender.getUser().isCorporate()) {
                transferFee = amount * 0.01;
            }
            else {
                transferFee = amount * 0.02;
            }
        }

        if (sender.getUser().isMilitary() && sender.getUser().isVip()) {
            transferFee *= 0.85;
        }
        else if (sender.getUser().isMilitary()) {
            transferFee *= 0.90;
        }
        else if (sender.getUser().isVip()) {
            transferFee *= 0.90;
        }

        return transferFee;
    }

    @Override
    public boolean isOverDailyTransactionLimit(Account account) {
        Iterable<Transaction> transactions = this.findBySenderAccount(account);
        List<Transaction> transactionList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().toLocalDate().equals(today)) {
                transactionList.add(transaction);
            }
        }
        return transactionList.size() >= account.getDailyTransactionLimit();
    }

    @Override
    public void createTransaction(Account sender, Account recipient, Transaction transaction) throws Exception {
        if (this.isOverDailyTransactionLimit(sender)) {
            throw new Exception("Account has reached the maximum allowed amount of daily transactions.");
        }
        Double amount = transaction.getAmount();
        Double transferFee = calculateTransferFee(sender, amount);
        sender.setBalance(sender.getBalance() - transferFee - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        transaction.setSenderAccount(sender);
        transaction.setRecipientAccount(recipient);
        this.save(transaction);
    }
}
