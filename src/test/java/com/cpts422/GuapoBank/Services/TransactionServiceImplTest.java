package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void TestFindAll() {

    }

    @Test
    void TestSave() {
    }

    @Test
    void TestFindBySenderAccount() {
    }

    @Test
    void TestFindByRecipientAccount() {
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