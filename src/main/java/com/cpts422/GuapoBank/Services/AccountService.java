package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Entities.User;

import java.util.Optional;

public interface AccountService {

    public Iterable<Account> findAll();

    public Account save(Account account);

    public Iterable<Account> findByUser(User user);

    public Optional<Account> findById(Long id);
}
