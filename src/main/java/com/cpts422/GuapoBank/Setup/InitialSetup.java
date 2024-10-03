package com.cpts422.GuapoBank.Setup;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Repositories.AccountRepository;
import com.cpts422.GuapoBank.Repositories.TransactionRepository;
import com.cpts422.GuapoBank.Repositories.UserRepository;
import com.cpts422.GuapoBank.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup implements CommandLineRunner {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public InitialSetup(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this. transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // user generation
        User normalUser = new User("TestUser", "password",
                "UserFirstName", "UserLastName", "User");

        User adminUser = new User("TestAdmin", "password",
                "AdminFirstName", "AdminLastName", "Admin");

        userRepository.save(normalUser);
        userRepository.save(adminUser);

        System.out.println("Initial users have been created and loaded.");
        System.out.println(userRepository);
        System.out.println(normalUser);
        System.out.println(adminUser);

        // account generation
        Account account1 = new Account("checking",500.00);
        Account account2 = new Account("savings",1000.00);

        normalUser.addAccount(account1);
        normalUser.addAccount(account2);

        accountRepository.save(account1);
        accountRepository.save(account2);

        userRepository.save(normalUser);
        userRepository.save(adminUser);

        System.out.println(account1);
        System.out.println(account2);

        // transaction generation
    }
}
