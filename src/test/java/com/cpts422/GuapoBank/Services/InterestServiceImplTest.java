package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterestServiceImplTest {

    @Mock
    private AccountService accountService;

    @Mock
    private InterestService interestService;

    @InjectMocks
    private InterestServiceImpl interestServiceImpl;

    @InjectMocks
    private InterestScheduler interestScheduler;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
    }

    @ParameterizedTest
    @CsvSource({
        "savings, 1000.0, 1020.0, false",   // Savings account with interest rate 0.02.
        "checking, 1000.0, 1000.0, false",  // Checking account with no interest.
        "savings, 1000.0, 1000.0, true",    // Frozen savings account no interest.
        "checking, 1000.0, 1000.0, true"    // Frozen checking account no interest.
    })
    // Test for applying interest to a specific account.
    void TestApplyInterest(String accountType, double initialBalance, double expectedBalance, boolean isFrozen) {
        Account testAccount = new Account(accountType, initialBalance, testUser);
        testAccount.setFrozen(isFrozen);

        interestServiceImpl.applyInterest(testAccount);

        assertEquals(expectedBalance, testAccount.getBalance());

        if (accountType.equals("savings") && !isFrozen) {
            verify(accountService, times(1)).save(testAccount);
        }
        else {
            verify(accountService, never()).save(testAccount);
        }
    }

    // Test for testing interest application to all accounts.
    @Test
    void TestApplyInterestToAll() {
        // Setup test accounts.
        Account savingsAccount1 = new Account("savings", 1000.0, testUser);
        Account savingsAccount2 = new Account("savings", 1000.0, testUser);
        Account checkingAccount = new Account("checking", 1000.0, testUser);
        Account frozenAccount = new Account("frozen", 1000.0, testUser);
        frozenAccount.setFrozen(true);
        savingsAccount2.setInterestRate(0.03);

        // Stub the mock service findAll method to return the list of test accounts.
        List<Account> accounts = Arrays.asList(savingsAccount1, savingsAccount2, checkingAccount, frozenAccount);
        when(accountService.findAll()).thenReturn(accounts);

        // Call the test method to apply interest to all accounts.
        interestServiceImpl.applyInterestToAll();

        // Assert that the balance of the test accounts match the expected balance.
        assertEquals(1020.0, savingsAccount1.getBalance());
        assertEquals(1030.0, savingsAccount2.getBalance());
        assertEquals(1000.0, checkingAccount.getBalance());
        assertEquals(1000.0, frozenAccount.getBalance());

        // Verify that the save method was called the valid number of times.
        verify(accountService, times(1)).save(savingsAccount1);
        verify(accountService, times(1)).save(savingsAccount2);
        verify(accountService, never()).save(checkingAccount);
        verify(accountService, never()).save(frozenAccount);
    }

    @Test
    void TestScheduleInterest() {
        interestScheduler.scheduleInterest();

        verify(interestService, times(1)).applyInterestToAll();
    }
}