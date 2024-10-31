package com.cpts422.GuapoBank.Entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    
    @ParameterizedTest
    @CsvSource({
            "savings, 1000.0, 0.02, 100.0",
            "checking, 500.0, 0.0, 50.0"
    })
    // Test case for testing the Account constructor.
    void testParameterizedConstructor(String accountType, double initialBalance, double expectedInterestRate, double expectedMinimumBalance) {
        User user = new User("user", "password", "FirstName", "LastName", "User");
        Account account = new Account(accountType, initialBalance, user);

        assertEquals(accountType, account.getAccountType());
        assertEquals(initialBalance, account.getBalance());
        assertEquals(expectedInterestRate, account.getInterestRate());
        assertEquals(expectedMinimumBalance, account.getMinimumBalance());
        assertFalse(account.isFrozen());
        assertEquals(user, account.getUser());
    }

    // Test for setting and getting account ID.
    @Test
    void testSetAndGetId() {
        Account account = new Account();
        Long expectedId = 123L;
        account.setId(expectedId);
        assertEquals(expectedId, account.getId());
    }

    // Test for setting and getting account type.
    @Test
    void testSetAndGetAccountType() {
        Account account = new Account();
        String expectedAccountType = "checking";
        account.setAccountType(expectedAccountType);
        assertEquals(expectedAccountType, account.getAccountType());
    }

    // Test for setting and getting account balance.
    @Test
    void TestSetAndGetBalance() {
        Account testAccount = new Account();
        testAccount.setBalance(200.0);
        assertEquals(200.0, testAccount.getBalance());
    }

    // Test for setting and getting account frozen status.
    @Test
    void TestSetAndGetFrozen() {
        Account testAccount = new Account();
        testAccount.setFrozen(true);
        assertTrue(testAccount.isFrozen());
    }

    @ParameterizedTest
    @CsvSource({
            "0.05, 0.05",
            "0.03, 0.03"
    })
    // Test for setting and getting account interest rate.
    void TestSetAndGetInterestRate(double interestRate, double expectedRate) {
        Account testAccount = new Account();
        testAccount.setInterestRate(interestRate);
        assertEquals(expectedRate, testAccount.getInterestRate());
    }

    @ParameterizedTest
    @CsvSource({
            "150.0, 150.0",
            "75.0, 75.0"
    })
    // Test for setting and getting minimum balance.
    void TestSetAndGetMinimumBalance(double minimumBalance, double expectedMinimumBalance) {
        Account testAccount = new Account();
        testAccount.setMinimumBalance(minimumBalance);
        assertEquals(expectedMinimumBalance, testAccount.getMinimumBalance());
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    // Test for setting and getting overdraft opt in.
    void TestSetAndGetOverdraftOptIn(boolean overdraftOptIn) {
        Account testAccount = new Account();
        testAccount.setOverdraftOptIn(overdraftOptIn);
        assertEquals(testAccount.isOverdraftOptIn(), overdraftOptIn);
    }

    @ParameterizedTest
    @CsvSource({
            "30.0, 30.0",
            "25.0, 25.0"
    })
    // Test for setting and getting overdraft fee.
    void TestSetAndGetOverdraftFee(double overdraftFee, double expectedOverdraftFee) {
        Account testAccount = new Account();
        testAccount.setOverdraftFee(overdraftFee);
        assertEquals(expectedOverdraftFee, testAccount.getOverdraftFee());
    }

    @ParameterizedTest
    @CsvSource({
            "corporate, 100",
            "vip, 15",
            "military, 10",
            "regular, 5"
    })
    // Test for testing the daily transaction limit for different user types.
    void testDailyTransactionLimitForUserTypes(String userType, int expectedLimit) {
        User user = null;

        switch (userType) {
            case "corporate" -> user = new User("corpUser", "password", "First", "Last", "User", false, false, true);
            case "vip" -> user = new User("vipUser", "password", "First", "Last", "User", true, false, false);
            case "military" -> user = new User("milUser", "password", "First", "Last", "User", false, true, false);
            case "regular" -> user = new User("regUser", "password", "First", "Last", "User", false, false, false);
        }

        Account account = new Account("checking", 1000.0, user);
        assertEquals(expectedLimit, account.getDailyTransactionLimit());
    }
}