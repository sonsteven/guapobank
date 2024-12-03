package com.cpts422.GuapoBank.Integration;

import com.cpts422.GuapoBank.Controllers.AdminController;
import com.cpts422.GuapoBank.Services.UserServiceImpl;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.InterestService;
import com.cpts422.GuapoBank.Repositories.UserRepository;
import com.cpts422.GuapoBank.Entities.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AdminController.class)
@ExtendWith(MockitoExtension.class)
@Import(UserServiceImpl.class)
public class TestPairAdminControllerUserService {

    @Autowired
    private AdminController adminController;

    // Mocking all other dependencies
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private InterestService interestService;

    @Mock
    private HttpSession session;

    @Test
    public void testAdminHomeRetrievesAllUsers() {
        User adminUser = new User();
        adminUser.setRole("Admin");
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        Model model = mock(Model.class);
        String viewName = adminController.adminHome(session, model);
        assertEquals("AdminHome", viewName);
        verify(userRepository).findAll();
        verify(model).addAttribute("users", users);
        verify(model).addAttribute("loggedInUser", adminUser);
    }

    @Test
    public void testViewUserAccountsRetrievesUserById() {
        User adminUser = new User();
        adminUser.setRole("Admin");
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Model model = mock(Model.class);
        String viewName = adminController.ViewUserAccounts(userId, model, session);
        assertEquals("ViewUserAccounts", viewName);
        verify(userRepository).findById(userId);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("accounts", user.getAccounts());
    }
}
