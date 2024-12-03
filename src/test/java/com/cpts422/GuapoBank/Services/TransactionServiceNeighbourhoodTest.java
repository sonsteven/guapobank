package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.TransactionRepository;
import com.cpts422.GuapoBank.Entities.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

// Testing class for createTransaction.
// We chose createTransaction for our neighbourhood system under test as it is essentially
// the backbone of most of the webapp's functionality.
public class TransactionServiceNeighbourhoodTest {

    private TransactionServiceImpl transactionService;
    private NotificationServiceImpl notificationService;
    private TransactionRepository transactionRepository;
    private Account sender;
    private Account recipient;
    private Transaction transaction;

    @BeforeEach
    void setup() {
        notificationService = mock(NotificationServiceImpl.class);
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionServiceImpl(transactionRepository, notificationService);

        User senderUser = new User();
        senderUser.setId(1L);
        senderUser.setUsername("Sender");

        sender = new Account("checking",1000.0,senderUser);
        sender.setId(1L);
        sender.setMinimumBalance(100.0);
        sender.setOverdraftOptIn(false);
        sender.setOverdraftFee(25.0);

        User recipientUser = new User();
        recipientUser.setId(2L);
        recipientUser.setUsername("Recipient");

        recipient = new Account("savings",500.0,recipientUser);
        recipient.setId(2L);

        transaction = new Transaction();
        transaction.setAmount(200.0);
    }

    // this case tests for calculateTransferFee(), sendNotification(), and save().
    @Test
    void createTransaction_success() throws Exception {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        transactionService.createTransaction(sender, recipient, transaction);

        assertEquals(790.0, sender.getBalance());
        assertEquals(700.0, recipient.getBalance());

        // this method is called from within transactionService.save()
        verify(transactionRepository, times(1)).save(transaction);

        verify(notificationService).sendNotification(
                eq("A transaction of $200.0 was sent from your checking account to Recipient's savings account."),
                eq(sender.getUser())
        );
        verify(notificationService).sendNotification(
                eq("Received a transaction of $200.0 from Sender's checking account to your savings account."),
                eq(recipient.getUser())
        );
    }

    // this case tests for isOverDailyTransactionLimit().
    @Test
    void createTransaction_over_daily_limit() {
        Account spySender = spy(sender);
        doReturn(0).when(spySender).getDailyTransactionLimit(); // NOT mocking isOverDailyTransactionLimit
        spySender.setBalance(1000.0);

        Exception exception = assertThrows(Exception.class, () -> {
            transactionService.createTransaction(spySender, recipient, transaction);
        });

        assertEquals("Account has reached the maximum allowed amount of daily transactions.", exception.getMessage());

        assertEquals(1000.0, spySender.getBalance());
        assertEquals(500.0, recipient.getBalance());

        verify(transactionRepository, never()).save(any(Transaction.class));
        verifyNoInteractions(notificationService);
    }
}
