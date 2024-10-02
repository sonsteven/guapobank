package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;

public interface AccountService {

    public Iterable<Account> findAll();

    public Account save(Account account);
}
