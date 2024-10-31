package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.annotations.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
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
    void TestAdminHome(String userType) {
        User loggedInUser = null;

        if ("testAdmin".equals(userType)) {
            loggedInUser = testAdmin;
        }
        else if ("testUser".equals(userType)) {
            loggedInUser = testUser;
        }

        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        String view = adminController.adminHome(session, model);

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

}