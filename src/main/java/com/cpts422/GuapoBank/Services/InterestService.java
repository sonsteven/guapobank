package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;


public interface InterestService {
    // Method to apply interest to an account.
    public void applyInterest(Account account);

    // Method to apply interest to all accounts.
    public void applyInterestToAll();
}
