package com.cpts422.GuapoBank.Repositories;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Iterable<Transaction> findBySenderAccount(Account senderAccount);
    Iterable<Transaction> findByRecipientAccount(Account recipientAccount);
    Iterable<Transaction> findBySenderAccountAndRecipientAccount(Account senderAccount, Account recipientAccount);
}
