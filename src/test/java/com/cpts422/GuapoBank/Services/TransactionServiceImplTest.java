package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private Transaction transaction;

    @Mock
    private Account account;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        notificationService = mock(NotificationService.class);
        transactionService = new TransactionServiceImpl(transactionRepository);
        transaction = mock(Transaction.class);
        when(transaction.getAmount()).thenReturn(100.00d);
    }

    @Test
    void TestFindAll() {
        Iterable<Transaction> transactions = List.of(transaction);
        when(transactionRepository.findAll()).thenReturn((List<Transaction>) transactions);

        Iterable<Transaction> foundTransactions = transactionService.findAll();
        assertEquals(transactions, foundTransactions);
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void TestSave() {
        Transaction savedTransaction = new Transaction();
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        savedTransaction = transactionService.save(transaction);
        assertEquals(savedTransaction, transaction);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void TestFindBySenderAccount() {
        Iterable<Transaction> transactions = List.of(transaction);
        when(transactionRepository.findBySenderAccount(account)).thenReturn(transactions);

        Iterable<Transaction> foundTransactions = transactionService.findBySenderAccount(account);
        assertEquals(transactions, foundTransactions);
        verify(transactionRepository, times(1)).findBySenderAccount(account);
    }

    @Test
    void TestFindByRecipientAccount() {
        Iterable<Transaction> transactions = List.of(transaction);
        when(transactionRepository.findByRecipientAccount(account)).thenReturn(transactions);

        Iterable<Transaction> foundTransactions = transactionService.findByRecipientAccount(account);
        assertEquals(transactions, foundTransactions);
        verify(transactionRepository, times(1)).findByRecipientAccount(account);
    }

    @Test
    void TestCalculateTransferFee() {
    }

    @Test
    void TestIsOverDailyTransactionLimit() {
    }

    @Test
    void TestCreateTransaction() {
    }
}