package com.cpts422.GuapoBank.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction, transaction2;

    @Mock
    private Account senderAccount;

    @Mock
    private Account recipientAccount;

    @BeforeEach
    void setUp() {
        senderAccount = mock(Account.class);
        recipientAccount = mock(Account.class);

        // basic constructor
        transaction = new Transaction();

        // constructor with args
        transaction2 = new Transaction(100.00d, senderAccount, recipientAccount);
    }

    @Test
    void TestGetSetId() {
        transaction.setId(1L);
        assertEquals(1L, transaction.getId());
    }

    @Test
    void TestGetSetAmount() {
        transaction.setAmount(100.00d);
        assertEquals(100.00d, transaction.getAmount());
    }

    @Test
    void TestGetTransactionDate() {
        // truncate to seconds since they'll be just a bit off otherwise
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), transaction2.getTransactionDate().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void TestGetSenderAccount() {
    }

    @Test
    void TestSetSenderAccount() {
    }

    @Test
    void TestGetRecipientAccount() {
    }

    @Test
    void TestSetRecipientAccount() {
    }
}