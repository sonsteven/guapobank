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