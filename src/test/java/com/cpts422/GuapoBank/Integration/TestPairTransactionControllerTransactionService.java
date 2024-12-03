package com.cpts422.GuapoBank.Integration;

import com.cpts422.GuapoBank.Controllers.TransactionController;
import com.cpts422.GuapoBank.Services.TransactionService;
import com.cpts422.GuapoBank.Services.TransactionServiceImpl;
import com.cpts422.GuapoBank.Repositories.TransactionRepository;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.NotificationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@WebMvcTest(TransactionController.class)
@ExtendWith(MockitoExtension.class)
@Import(TransactionServiceImpl.class)
public class TestPairTransactionControllerTransactionService {

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private TransactionService transactionService;

    // Mocking all other dependencies
    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private NotificationService notificationService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Test
    public void testCreateTransactionInsufficientFunds() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(1000.0); // Over sender balance
        Account senderAccount = new Account();
        senderAccount.setId(1L);
        senderAccount.setBalance(500.0); // Sender balance
        senderAccount.setUser(new User());

        Account recipientAccount = new Account();
        recipientAccount.setId(2L);
        recipientAccount.setBalance(200.0);
        recipientAccount.setUser(new User());

        transaction.setSenderAccount(senderAccount);
        transaction.setRecipientAccount(recipientAccount);
        when(accountService.findById(1L)).thenReturn(Optional.of(senderAccount));
        when(accountService.findById(2L)).thenReturn(Optional.of(recipientAccount));

        redirectAttributes = mock(RedirectAttributes.class);
        String viewName = transactionController.createTransaction(transaction, redirectAttributes);

        assertEquals("redirect:/home", viewName);
        verify(accountService).findById(1L);
        verify(accountService).findById(2L);
        verify(redirectAttributes).addFlashAttribute(eq("error"), contains("Insufficient funds, account overdraft is not enabled."));
    }
}
