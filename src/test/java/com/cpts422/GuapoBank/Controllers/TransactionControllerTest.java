package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.AccountServiceImpl;
import com.cpts422.GuapoBank.Services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

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
        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.of(senderAccount));
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.of(recipientAccount));

        String controller = transactionController.createTransaction(transaction);
        verify(transactionService, times(1)).createTransaction(any(), any(), any());
        assertEquals("redirect:/home", controller);
    }

    @Test
    void TestCreateTransactionInvalidRecipient() throws Exception {
        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.of(senderAccount));
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.empty());

        String controller = transactionController.createTransaction(transaction);
        verifyNoInteractions(transactionService);
        assertEquals("redirect:/home", controller);
    }

    @Test
    void TestCreateTransactionInvalidSender() throws Exception {
        when(senderAccount.getId()).thenReturn(1L);
        when(recipientAccount.getId()).thenReturn(2L);

        when(transaction.getSenderAccount()).thenReturn(senderAccount);
        when(transaction.getRecipientAccount()).thenReturn(recipientAccount);
        when(accountService.findById(senderAccount.getId())).thenReturn(Optional.empty());
        when(accountService.findById(recipientAccount.getId())).thenReturn(Optional.of(recipientAccount));

        String controller = transactionController.createTransaction(transaction);
        verifyNoInteractions(transactionService);
        assertEquals("redirect:/home", controller);
    }
}