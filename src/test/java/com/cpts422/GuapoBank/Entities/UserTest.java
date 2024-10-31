package com.cpts422.GuapoBank.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    private User user;
    private User user2 = new User();
    private User user3 = new User("TestUser", "password",
            "UserFirstName", "UserLastName", "User");

    @Mock
    private Account account;

    private List<Account> accounts;

    @BeforeEach
    void setUp() {
        user = new User("TestUser", "password",
                "UserFirstName", "UserLastName", "User",
                false, false, false);

        account = mock(Account.class);
        accounts = new ArrayList<>();
    }

    @Test
    void TestGetSetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void TestAddAccount() {
        user.addAccount(account);
        assertTrue(user.getAccounts().contains(account));
        verify(account, times(1)).setUser(user);
    }

    @Test
    void TestGetSetAccounts() {
        user.setAccounts(accounts);
        assertEquals(accounts, user.getAccounts());
    }

    @Test
    void TestGetSetPassword() {
        user.setPassword("pa55w0rd");
        assertEquals("pa55w0rd", user.getPassword());
    }

    @Test
    void TestGetSetUsername() {
        user.setUsername("testUserrrr");
        assertEquals("testUserrrr", user.getUsername());
    }

    @Test
    void TestGetSetFirstName() {
        user.setFirstName("Bryce");
        assertEquals("Bryce", user.getFirstName());
    }

    @Test
    void TestGetSetLastName() {
        user.setLastName("Moser");
        assertEquals("Moser", user.getLastName());
    }

    @Test
    void TestGetSetRole() {
        user.setRole("Admin");
        assertEquals("Admin", user.getRole());
    }

//    @Test
//    void testToString() {
//    }

    @Test
    void TestIsSetVip() {
        user.setVip(true);
        assertTrue(user.isVip());
    }

    @Test
    void TestIsSetMilitary() {
        user.setMilitary(true);
        assertTrue(user.isMilitary());
    }

    @Test
    void TestIsSetCorporate() {
        user.setCorporate(true);
        assertTrue(user.isCorporate());
    }

}