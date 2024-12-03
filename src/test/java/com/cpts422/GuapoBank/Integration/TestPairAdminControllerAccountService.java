package com.cpts422.GuapoBank.Integration;

import com.cpts422.GuapoBank.Controllers.AdminController;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.AccountServiceImpl;
import com.cpts422.GuapoBank.Services.UserService;
import com.cpts422.GuapoBank.Services.InterestService;
import com.cpts422.GuapoBank.Repositories.AccountRepository;
import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AdminController.class)
@ExtendWith(MockitoExtension.class)
@Import(AccountServiceImpl.class)
public class TestPairAdminControllerAccountService {

    @Autowired
    private AdminController adminController;

    @Autowired
    private AccountService accountService;

    // Mocking all other dependencies
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private InterestService interestService;

    @Mock
    private HttpSession session;

    @Test
    public void testFreezeAccount() {
        User adminUser = new User();
        adminUser.setRole("Admin");
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        String redirectUrl = adminController.freezeAccount(accountId, session);

        assertEquals("redirect:/admin/user/{id}/accounts", redirectUrl);
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        assertTrue(account.isFrozen());
    }

    @Test
    public void testUnfreezeAccount() {
        User adminUser = new User();
        adminUser.setRole("Admin");
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setFrozen(true);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        String redirectUrl = adminController.unfreezeAccount(accountId, session);

        assertEquals("redirect:/admin/user/{id}/accounts", redirectUrl);
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        assertFalse(account.isFrozen());
    }
}
