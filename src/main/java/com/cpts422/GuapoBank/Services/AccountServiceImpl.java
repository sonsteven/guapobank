package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Repositories.AccountRepository;

public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
