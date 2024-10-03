package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestServiceImpl implements InterestService {

    @Autowired
    private AccountService accountService;

    // Method for applying interest to one account.
    @Override
    public void applyInterest(Account account) {
        if (account.getAccountType().equalsIgnoreCase("savings") && !account.isFrozen()) {
            Double currentBalance = account.getBalance();
            double interest = currentBalance * account.getInterestRate();
            account.setBalance(currentBalance + interest);
            accountService.save(account);
        }
    }

    // Method for applying interest to all accounts.
    @Override
    public void applyInterestToAll() {
        Iterable<Account> accounts = accountService.findAll();
        for (Account account : accounts) {
            applyInterest(account);
        }
    }
}
