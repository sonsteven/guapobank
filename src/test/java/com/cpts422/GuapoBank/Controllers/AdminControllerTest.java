package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AccountService accountService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    private User testUser;
    private User testAdmin;

    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password", "FirstName", "LastName", "User");
        testAdmin = new User("testAdmin", "password", "FirstName", "LastName", "Admin");

        testUser.setId(1L);
        testAdmin.setId(2L);
    }

    @ParameterizedTest
    @CsvSource({
            "testAdmin",
            "testUser",
            "null"
    })
    // Test for the AdminController adminHome method.
    void TestAdminHome(String userType) {
        User loggedInUser = null;

        // Assign loggedInUser based on parameterized input.
        if ("testAdmin".equals(userType)) {
            loggedInUser = testAdmin;
        }
        else if ("testUser".equals(userType)) {
            loggedInUser = testUser;
        }

        // Stub the mock session getAttribute return as the loggedInUser.
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        // Call the test method.
        String view = adminController.adminHome(session, model);

        // Assert view is correct and model update methods are called correctly based on userType.
        if ("testAdmin".equals(userType)) {
            assertEquals("AdminHome", view);
            verify(model, times(1)).addAttribute(eq("users"), any());
            verify(model, times(1)).addAttribute("loggedInUser", testAdmin);
        }
        else {
            assertEquals("redirect:/login", view);
            verify(model, never()).addAttribute(eq("users"), any());
        }
    }

    @ParameterizedTest
    @CsvSource({
        // User type, user exists boolean, expectedView
        "testAdmin, true, ViewUserAccounts",
        "testAdmin, false, redirect:/admin/home",
        "testUser, true, redirect:/login",
        "null, true, redirect:/login"
    })
    // Test for the AdminController ViewUserAccounts method.
    void TestViewUserAccounts(String userType, boolean userExists, String expectedView) {
        User loggedInUser = null;
        Optional<User> requestedUser = userExists ? Optional.of(testUser): Optional.empty();

        // Set loggedInUser based on parameterized input.
        if ("testAdmin".equals(userType)) {
            loggedInUser = testAdmin;
        }
        else if ("testUser".equals(userType)) {
            loggedInUser = testUser;
        }

        // Stub the mock session getAttribute method to return the loggedInUser.
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        // If testing as an admin stub the mock userService findById method to return the requestedUser.
        if ("testAdmin".equals(userType)) {
            when(userService.findById(1L)).thenReturn(requestedUser);
        }

        // Call the test method.
        String view = adminController.ViewUserAccounts(1L, model, session);

        // Assert that the expected view is equal to the result view.
        assertEquals(expectedView, view);

        // Verify model update methods are called accordingly based on user type and if user exists.
        if ("testAdmin".equals(userType) && userExists) {
            verify(model, times(1)).addAttribute("user", testUser);
            verify(model, times(1)).addAttribute("accounts", testUser.getAccounts());
        }
        else {
            verify(model, never()).addAttribute(eq("user"), any());
            verify(model, never()).addAttribute(eq("accounts"), any());
        }

        // Verify userService method was called accordingly based on user type.
        if ("testAdmin".equals(userType)) {
            verify(userService, times(1)).findById(1L);
        }
        else {
            verify(userService, never()).findById(any());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "testAdmin, true, redirect:/admin/user/{id}/accounts",
            "testAdmin, false, redirect:/admin/user/{id}/accounts",
            "testUser, true, redirect:/login",
            "null, true, redirect:/login"
    })
    // Test freezing an account.
    void TestFreezeAccount(String userType, boolean accountExists, String expectedView) {
        User loggedInUser = null;
        Optional<Account> requestedAccount = accountExists ? Optional.of(new Account()) : Optional.empty();

        // Determine loggedInUser based on parameterized input.
        if ("testAdmin".equals(userType)) {
            loggedInUser = testAdmin;
        }
        else if ("testUser".equals(userType)) {
            loggedInUser = testUser;
        }

        // Stub the mock session getAttribute method to return the loggedInUser.
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        // If testing as an admin stub the mock account service findById to return the requested account.
        if ("testAdmin".equals(userType)) {
            when(accountService.findById(1L)).thenReturn(requestedAccount);
        }

        // Test the admin controller freeze account method.
        String view = adminController.freezeAccount(1L, session);

        // Assert expected view is equal to result view.
        assertEquals(expectedView, view);

        // Based on user type and if account exists assert and verify frozen status and account update method calls.
        if ("testAdmin".equals(userType) && accountExists) {
            Account account = requestedAccount.get();
            assertTrue(account.isFrozen());
            verify(accountService, times(1)).save(account);
        }
        else {
            verify(accountService, never()).save(any());
        }

        // Based on userType verify the account service call.
        if ("testAdmin".equals(userType)) {
            verify(accountService, times(1)).findById(1L);
        }
        else {
            verify(accountService, never()).findById(any());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "testAdmin, true, redirect:/admin/user/{id}/accounts",
            "testAdmin, false, redirect:/admin/home",
            "testUser, true, redirect:/login",
            "null, true, redirect:/login"
    })
        // Test unfreezing an account.
    void TestUnfreezeAccount(String userType, boolean accountExists, String expectedView) {
        User loggedInUser = null;
        Optional<Account> requestedAccount = accountExists ? Optional.of(new Account()) : Optional.empty();

        // Determine loggedInUser based on parameterized input.
        if ("testAdmin".equals(userType)) {
            loggedInUser = testAdmin;
        }
        else if ("testUser".equals(userType)) {
            loggedInUser = testUser;
        }

        // Stub the mock session getAttribute method to return the loggedInUser.
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        // If testing as an admin stub the mock account service findById to return the requested account.
        if ("testAdmin".equals(userType)) {
            when(accountService.findById(1L)).thenReturn(requestedAccount);
        }

        // Test the admin controller freeze account method.
        String view = adminController.unfreezeAccount(1L, session);

        // Assert expected view is equal to result view.
        assertEquals(expectedView, view);

        // Based on user type and if account exists assert and verify frozen status and account update method calls.
        if ("testAdmin".equals(userType) && accountExists) {
            Account account = requestedAccount.get();
            assertFalse(account.isFrozen());
            verify(accountService, times(1)).save(account);
        }
        else {
            verify(accountService, never()).save(any());
        }

        // Based on userType verify the account service call.
        if ("testAdmin".equals(userType)) {
            verify(accountService, times(1)).findById(1L);
        }
        else {
            verify(accountService, never()).findById(any());
        }
    }
}