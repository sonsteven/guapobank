package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/createTransaction")
    public String showTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "createTransaction";
    }

    @PostMapping("/createTransaction")
    public String createTransaction(@ModelAttribute("transaction") Transaction transaction) throws Exception {
        Optional<Account> senderOpt = accountService.findById(transaction.getSenderAccount().getId());
        Optional<Account> recipientOpt = accountService.findById(transaction.getRecipientAccount().getId());

        Account sender = new Account();
        Account recipient = new Account();

        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            sender = senderOpt.get();
            recipient = recipientOpt.get();
        }
        else {
            return "redirect:/home"; // error handling needs to be implemented

        }

        if (transaction.getAmount() > sender.getBalance()) {
            return "redirect:/home"; // error handling needs to be implemented
        }

        if (sender.isFrozen() || recipient.isFrozen()) {
            return "redirect:/home"; // error handling needs to be implemented
        }

        try {
            transactionService.createTransaction(sender, recipient, transaction);
        } catch (Exception e) {
            // TODO: display error message on frontend
        }
        return "redirect:/home";
    }
}
