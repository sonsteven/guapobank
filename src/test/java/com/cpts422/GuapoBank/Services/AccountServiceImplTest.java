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
        // Stub the accountRepository save method to return the test account.
        when(accountRepository.save(account)).thenReturn(account);

        // Call the test method to save the test account.
        Account savedAccount = accountService.save(account);

        // Assert that the test account is equal to the saved account, then verify the test method was only called once.
        assertEquals(account, savedAccount);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void TestFindByUser() {
        // Create list of expected accounts to stub the mock repository findByUser method return with.
        List<Account> expectedAccounts = Arrays.asList(account);
        when(accountRepository.findByUser(user)).thenReturn(expectedAccounts);

        // Call the test method to find the accounts by the test user.
        Iterable<Account> result = accountService.findByUser(user);

        // Assert the test result accounts are equal to the expected accounts and verify the test method was called once.
        assertEquals(expectedAccounts, result);
        verify(accountRepository, times(1)).findByUser(user);
    }

    @Test
    void TestFindById() {
        

    }

}