package com.cpts422.GuapoBank.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction;

    @Mock
    private Account senderAccount;

    @Mock
    private Account recipientAccount;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        senderAccount = mock(Account.class);
        recipientAccount = mock(Account.class);
    }

    @Test
    void TestGetSetId() {
        transaction.setId(1L);
        assertEquals(1L, transaction.getId());
    }

    @Test
    void TestGetAmount() {
    }

    @Test
    void TestSetAmount() {
    }

    @Test
    void TestGetTransactionDate() {
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