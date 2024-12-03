package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.AccountServiceImpl;
import com.cpts422.GuapoBank.Services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private Model model;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private Transaction transaction;

    @Mock
    private Account senderAccount;

    @Mock
    private Account recipientAccount;

    @BeforeEach
    void setUp() {
    }

    @Test
    void TestShowTransactionForm() {
        String controller = transactionController.showTransactionForm(model);
        assertEquals("createTransaction", controller);
        verify(model, times(1)).addAttribute(eq("transaction"), any(Transaction.class));
    }

    @Test
    void TestCreateTransactionSuccess() throws Exception {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.of(senderAccount));
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.of(recipientAccount));

        String controller = transactionController.createTransaction(transaction, redirectAttributes);
        verify(transactionService, times(1)).createTransaction(any(), any(), any());
        assertEquals("redirect:/home", controller);
    }

    @Test
    void TestCreateTransactionInvalidRecipient() throws Exception {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.of(senderAccount));
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.empty());

        String controller = transactionController.createTransaction(transaction, redirectAttributes);
        verifyNoInteractions(transactionService);
        assertEquals("redirect:/home", controller);
    }

    @Test
    void TestCreateTransactionInvalidSender() throws Exception {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.empty());
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.of(recipientAccount));

        String controller = transactionController.createTransaction(transaction, redirectAttributes);
        verifyNoInteractions(transactionService);
        assertEquals("redirect:/home", controller);
    }

    @Test
    void TestCreateTransactionOverBalance() throws Exception {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.of(senderAccount));
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.of(recipientAccount));

        when(transaction.getAmount()).thenReturn(9001.00d);
        when(senderAccount.getBalance()).thenReturn(100.00d);
        String controller = transactionController.createTransaction(transaction, redirectAttributes);
        verifyNoInteractions(transactionService);
        assertEquals("redirect:/home", controller);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2})
    void TestCreateTransactionFrozenAccount(int test) throws Exception {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.of(senderAccount));
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.of(recipientAccount));

        if (test == 1) {
            when(senderAccount.isFrozen()).thenReturn(true);
        }
        else {
            when(recipientAccount.isFrozen()).thenReturn(true);
        }

        String controller = transactionController.createTransaction(transaction, redirectAttributes);
        verifyNoInteractions(transactionService);
        assertEquals("redirect:/home", controller);
    }

    @Test
    void TestCreateTransactionInvalidTransaction() throws Exception {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.of(senderAccount));
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.of(recipientAccount));
        doThrow(new Exception()).when(transactionService).createTransaction(any(), any(), any());

        String controller = transactionController.createTransaction(transaction, redirectAttributes);
        verify(transactionService, times(1)).createTransaction(any(), any(), any());
        assertEquals("redirect:/home", controller);
    }
}