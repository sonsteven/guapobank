package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        account = new Account("checking", 500.0, user);
    }

    // Tests the findAll() method in the account service.
    @Test
    void TestFindAll() {
        // Create second account for testing and expected list of accounts.
        Account testAccount = new Account("savings", 500.0, user);
        List<Account> expectedAccounts = Arrays.asList(account, testAccount);

        // Stub the account repository findAll method to return the list of accounts.
        when(accountRepository.findAll()).thenReturn(expectedAccounts);

        // Call the account service test method.
        Iterable<Account> result = accountService.findAll();

        // Assert the result matches the expected result and verify the test method was only called once.
        assertEquals(expectedAccounts, result);
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void TestSaveAccount() {

    }

    @Test
    void TestFindByUser() {

    }

    @Test
    void TestFindById() {

    }

}