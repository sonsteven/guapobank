package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.*;
import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.TransactionRepository;
import com.cpts422.GuapoBank.Entities.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.AccountServiceImpl;
import com.cpts422.GuapoBank.Services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void createTransaction_success() throws Exception {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        transactionService.createTransaction(sender, recipient, transaction);

        assertEquals(790.0, sender.getBalance());
        assertEquals(700.0, recipient.getBalance());

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
}
