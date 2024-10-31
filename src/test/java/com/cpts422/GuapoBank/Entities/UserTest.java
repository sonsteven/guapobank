package com.cpts422.GuapoBank.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    @BeforeEach
    void setUp() {
        user = new User("TestUser", "password",
                "UserFirstName", "UserLastName", "User", false, false, false);
    }

    @Test
    void TestGetSetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void TestAddAccount() {
    }

    @Test
    void TestGetSetAccounts() {
    }

    @Test
    void TestGetSetPassword() {
    }

    @Test
    void TestGetSetUsername() {
    }

    @Test
    void TestGetSetFirstName() {
    }

    @Test
    void TestGetSetLastName() {
    }

    @Test
    void TestGetSetRole() {
    }

    @Test
    void createTransaction() {
    }

//    @Test
//    void testToString() {
//    }

    @Test
    void TestIsSetVip() {
    }

    @Test
    void TestIsSetMilitary() {
    }

    @Test
    void TestIsSetCorporate() {
    }

}