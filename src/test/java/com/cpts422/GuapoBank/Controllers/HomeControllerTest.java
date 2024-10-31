package com.cpts422.GuapoBank.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.NotificationService;
import com.cpts422.GuapoBank.Services.TransactionService;
import com.cpts422.GuapoBank.Services.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import java.util.*;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    private User admin;

    private User user;

    private Account account;

    private Transaction sentTransaction;

    private Transaction receivedTransaction;

    @BeforeEach
    void setUp() throws Exception {
        admin = new User();
        admin.setRole("Admin");
        user = new User();
        user.setRole("User");
        account = new Account();
        sentTransaction = new Transaction();
        receivedTransaction = new Transaction();

        Field notificationServiceField = HomeController.class.getDeclaredField("notificationService");
        notificationServiceField.setAccessible(true);
        notificationServiceField.set(homeController, notificationService);
    }

    @Test
    void test_index_redirectToLogin() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);
        String result = homeController.index(session);
        assertEquals("redirect:/login", result);
    }

    @Test
    void test_index_redirectToAdminHome() {
        when(session.getAttribute("loggedInUser")).thenReturn(admin);
        String result = homeController.index(session);
        assertEquals("redirect:/admin/home", result);
    }

    @Test
    void test_index_redirectToUserHome() {
        when(session.getAttribute("loggedInUser")).thenReturn(user);
        String result = homeController.index(session);
        assertEquals("redirect:/home", result);
    }

    @Test
    void test_login_notLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);
        String result = homeController.login(session, model);
        assertEquals("Login", result);
    }

    @Test
    void test_login_redirectToAdminHome() {
        when(session.getAttribute("loggedInUser")).thenReturn(admin);
        String result = homeController.login(session, model);
        assertEquals("redirect:/admin/home", result);
    }

    @Test
    void test_login_redirectToUserHome() {
        when(session.getAttribute("loggedInUser")).thenReturn(user);
        String result = homeController.login(session, model);
        assertEquals("redirect:/home", result);
    }

    @Test
    void test_authenticate_invalidCredentials() {
        String username = "invalid";
        String password = "12345";
        when(userService.authenticate(username, password)).thenReturn(null);
        String result = homeController.authenticate(username, password, model, session);

        assertEquals("Login", result);
        verify(model).addAttribute("error", "Invalid username or password");
    }

    @Test
    void test_authenticate_adminLogin() {
        String username = "admin";
        String password = "adminPass";
        when(userService.authenticate(username, password)).thenReturn(admin);
        String result = homeController.authenticate(username, password, model, session);

        assertEquals("redirect:/admin/home", result);
        verify(session).setAttribute("loggedInUser", admin);
        verify(notificationService).sendNotification(anyString(), eq(admin));
    }

    @Test
    void test_authenticate_userLogin() {
        String username = "user";
        String password = "userPass";
        when(userService.authenticate(username, password)).thenReturn(user);
        String result = homeController.authenticate(username, password, model, session);

        assertEquals("redirect:/home", result);
        verify(session).setAttribute("loggedInUser", user);
        verify(notificationService).sendNotification(anyString(), eq(user));
    }

    @Test
    void test_home_userNotLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);
        String result = homeController.home(session, model);
        assertEquals("redirect:/login", result);
    }

    @Test
    void test_home_redirectToLoginAdmin() {
        when(session.getAttribute("loggedInUser")).thenReturn(admin);
        String result = homeController.home(session, model);
        assertEquals("redirect:/login", result);
    }

    @Test
    void test_home_displayUserHomeWithData() {
        when(session.getAttribute("loggedInUser")).thenReturn(user);
        when(accountService.findByUser(user)).thenReturn(Arrays.asList(account));
        when(transactionService.findBySenderAccount(account)).thenReturn(Arrays.asList(sentTransaction));
        when(transactionService.findByRecipientAccount(account)).thenReturn(Arrays.asList(receivedTransaction));
        String result = homeController.home(session, model);

        assertEquals("Home", result);
        verify(model).addAttribute("loggedInUser", user);
        verify(model).addAttribute("accounts", Arrays.asList(account));
        verify(model).addAttribute(eq("transactionHistory"), any(List.class));
    }

    @Test
    void test_logout_redirectToLogin() {
        String result = homeController.logout(session);
        assertEquals("redirect:/login", result);
        verify(session).invalidate();
    }
}