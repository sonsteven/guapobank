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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterestServiceImplTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private InterestServiceImpl interestService;

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
    void TestApplyInterest(String accountType, double initialBalance, double expectedBalance, boolean isFrozen) {
        Account testAccount = new Account(accountType, initialBalance, testUser);
        testAccount.setFrozen(isFrozen);

        interestService.applyInterest(testAccount);

        assertEquals(expectedBalance, testAccount.getBalance());

        if (accountType.equals("savings") && !isFrozen) {
            verify(accountService, times(1)).save(testAccount);
        }
        else {
            verify(accountService, never()).save(testAccount);
        }
    }
}