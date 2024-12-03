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

    @Autowired
    private NotificationService notificationService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
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
        return transactionList.size() > account.getDailyTransactionLimit();
    }

    @Override
    public void createTransaction(Account sender, Account recipient, Transaction transaction) throws Exception {
        if (this.isOverDailyTransactionLimit(sender)) {
            throw new Exception("Account has reached the maximum allowed amount of daily transactions.");
        }
        Double amount = transaction.getAmount();
        Double transferFee = calculateTransferFee(sender, amount);
        Double remainingBalance = sender.getBalance() - amount - transferFee;

        // Check if transaction will cause account to go below minimum balance.
        // Ignore if account is opted in Overdraft.

        if (!sender.isOverdraftOptIn() && remainingBalance < sender.getMinimumBalance()) {
            throw new Exception("Transaction will cause account to go below minimum balance.");
        }

        if (remainingBalance < 0 && !sender.isOverdraftOptIn()) {
            throw new Exception("Insufficient funds, account overdraft is not enabled.");
        }

        if (remainingBalance < 0) {
            remainingBalance -= sender.getOverdraftFee();
        }

        sender.setBalance(remainingBalance);

        // Update the recipient's balance.
        recipient.setBalance(recipient.getBalance() + amount);


        transaction.setSenderAccount(sender);
        transaction.setRecipientAccount(recipient);
        this.save(transaction);

        String senderAccountType = sender.getAccountType();
        String recipientAccountType = recipient.getAccountType();

        if (sender.getUser().getId() == recipient.getUser().getId()) {
            // internal transfer
            notificationService.sendNotification("Transfer of $" + amount + " from your " + senderAccountType + " account to your " + recipientAccountType + " account.", sender.getUser());
        }
        else {
            // transfer between different users
            notificationService.sendNotification("A transaction of $" + amount + " was sent from your " + senderAccountType + " account to " + recipient.getUser().getUsername() + "'s " + recipientAccountType + " account.", sender.getUser());
            notificationService.sendNotification("Received a transaction of $" + amount + " from " + sender.getUser().getUsername() + "'s " + senderAccountType + " account to your " + recipientAccountType + " account.", recipient.getUser());
        }
    }
}
