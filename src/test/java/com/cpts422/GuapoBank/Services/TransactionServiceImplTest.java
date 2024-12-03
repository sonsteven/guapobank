package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
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

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        notificationService = mock(NotificationService.class);
        transactionService = new TransactionServiceImpl(transactionRepository, notificationService);
        transaction = mock(Transaction.class);
        account = mock(Account.class);
        user = mock(User.class);
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

    @ParameterizedTest
    @ValueSource(doubles = {500.00d, 1000.00d, 3000.00d, 5000.00d, 5001.00d})
    void TestCalculateTransferFeeAmounts(double amount) {
        when(account.getUser()).thenReturn(user);
        when(account.getUser().isCorporate()).thenReturn(false);
        when(account.getUser().isMilitary()).thenReturn(false);
        when(account.getUser().isVip()).thenReturn(false);

        double expectedFee = 0.0d;
        if (amount == 500.00d) {
            expectedFee = 25.00d;
        }
        else if (amount == 1000.00d) {
            expectedFee = 40.00d;
        }
        else if (amount == 3000.00d) {
            expectedFee = 90.00d;
        }
        else if (amount == 5000.00d) {
            expectedFee = 125.00d;
        }
        else if (amount == 5001.00d) {
            expectedFee = 100.02d;
        }

        assertEquals(expectedFee, transactionService.calculateTransferFee(account, amount), 0.01);
    }

    @Test
    void TestCalculateTransferFeeCorporate() {
        when(account.getUser()).thenReturn(user);
        when(account.getUser().isCorporate()).thenReturn(true);
        double amount = 5001.00d;
        double expectedFee = 50.01d;
        assertEquals(expectedFee, transactionService.calculateTransferFee(account, amount), 0.01);
    }

    @Test
    void TestCalculateTransferFeeMilitaryAndVip() {
        when(account.getUser()).thenReturn(user);
        when(account.getUser().isMilitary()).thenReturn(true);
        when(account.getUser().isVip()).thenReturn(true);
        double amount = 100.00d;
        double expectedFee = 4.25d;
        assertEquals(expectedFee, transactionService.calculateTransferFee(account, amount), 0.01);
    }

    @Test
    void TestCalculateTransferFeeMilitary() {
        when(account.getUser()).thenReturn(user);
        when(account.getUser().isMilitary()).thenReturn(true);
        when(account.getUser().isVip()).thenReturn(false);
        double amount = 100.00d;
        double expectedFee = 4.50d;
        assertEquals(expectedFee, transactionService.calculateTransferFee(account, amount), 0.01);
    }

    @Test
    void TestCalculateTransferFeeVip() {
        when(account.getUser()).thenReturn(user);
        when(account.getUser().isMilitary()).thenReturn(false);
        when(account.getUser().isVip()).thenReturn(true);
        double amount = 100.00d;
        double expectedFee = 4.50d;
        assertEquals(expectedFee, transactionService.calculateTransferFee(account, amount), 0.01);
    }

//    @Test
//    void TestIsOverDailyTransactionLimitTrue() {
//        when(transaction.getTransactionDate()).thenReturn(LocalDateTime.now());
//        Iterable<Transaction> transactions = List.of(transaction);
//        when(transactionRepository.findBySenderAccount(account)).thenReturn(transactions);
//        when(account.getDailyTransactionLimit()).thenReturn(1);
//
//        verify(transaction, times(1)).getTransactionDate();
//        verify(transactionRepository, times(1)).findBySenderAccount(account);
//        verify(account,times(1)).getDailyTransactionLimit();
//    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void TestIsOverDailyTransactionLimit(int test) {
        if (test <= 2) {
            when(transaction.getTransactionDate()).thenReturn(LocalDateTime.now());
        }
        else {
            // not in past 24h
            when(transaction.getTransactionDate()).thenReturn(LocalDateTime.of(2024, 10, 6, 0, 0));
        }
        Iterable<Transaction> transactions = List.of(transaction);
        when(transactionRepository.findBySenderAccount(account)).thenReturn(transactions);

        if (test == 1) {
            when(account.getDailyTransactionLimit()).thenReturn(1);
            assertFalse(transactionService.isOverDailyTransactionLimit(account));
        }
        else {
            when(account.getDailyTransactionLimit()).thenReturn(2);
            assertFalse(transactionService.isOverDailyTransactionLimit(account));
        }
        verify(transaction, times(1)).getTransactionDate();
        verify(transactionRepository, times(1)).findBySenderAccount(account);
        verify(account,times(1)).getDailyTransactionLimit();
    }

    @Test
    void TestCreateTransaction() throws Exception {
        Account account2 = mock(Account.class);
        User user2 = mock(User.class);

        when(account.getUser()).thenReturn(user);
        when(transaction.getTransactionDate()).thenReturn(LocalDateTime.now());
        when(account.getDailyTransactionLimit()).thenReturn(1);

        // minimum balance exception
        Exception e = assertThrows(Exception.class, () -> transactionService.createTransaction(account, account2, transaction));


        // normal case, transfer between one user
        when(account.getBalance()).thenReturn(1000.00d);

        when(account2.getUser()).thenReturn(user);
        when(account.getUser().getId()).thenReturn(1L);
        when(account2.getUser().getId()).thenReturn(1L);
        transactionService.createTransaction(account, account2, transaction);

        when(account.isOverdraftOptIn()).thenReturn(true);
        account.setOverdraftOptIn(true);
        transactionService.createTransaction(account, account2, transaction);

        // transaction with overdraft fee
        when(transaction.getAmount()).thenReturn(9001.00d);
        transactionService.createTransaction(account, account2, transaction);

        // normal case, transfer between two users
        when(account.getBalance()).thenReturn(1000.00d);

        when(account2.getUser()).thenReturn(user2);
        when(account.getUser().getId()).thenReturn(1L);
        when(account2.getUser().getId()).thenReturn(2L);
        transactionService.createTransaction(account, account2, transaction);

        // daily limit exception
        Iterable<Transaction> transactions = List.of(transaction);
        when(transactionRepository.findBySenderAccount(account)).thenReturn(transactions);

        e = assertThrows(Exception.class, () -> transactionService.createTransaction(account, account2, transaction));
    }
}