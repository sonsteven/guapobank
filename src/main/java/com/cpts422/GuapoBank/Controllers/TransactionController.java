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
    public String createTransaction(@ModelAttribute("transaction") Transaction transaction) {
        Optional<Account> senderOpt = accountService.findById(transaction.getSenderAccount().getId());
        Optional<Account> recipientOpt = accountService.findById(transaction.getRecipientAccount().getId());

        Account sender = new Account();
        Account recipient = new Account();

        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            sender = senderOpt.get();
            recipient = recipientOpt.get();
        }
        else {
            // error
        }

        if (transaction.getAmount() > sender.getBalance()) {
            // error
        }

        Double amount = transaction.getAmount();

        // calculate transfer fee
        Double transferFee = 0.0d;
        if (amount <= 500) {
            transferFee = amount * 0.05;
        }
        else if (amount <= 1000) {
            transferFee = amount * 0.04;
        }
        else if (amount <= 3000) {
            transferFee = amount * 0.03;
        }
        else if (amount <= 5000) {
            transferFee = amount * 0.025;
        }
        else {
            transferFee = amount * 0.02;
        }



        sender.setBalance(sender.getBalance() - transferFee - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        transaction.setSenderAccount(sender);
        transaction.setRecipientAccount(recipient);
        transactionService.save(transaction);
        return "redirect:/home";
    }
}
