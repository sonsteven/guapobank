package com.cpts422.GuapoBank.Setup;

import com.cpts422.GuapoBank.Repositories.UserRepository;
import com.cpts422.GuapoBank.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup implements CommandLineRunner {
    private final UserRepository userRepository;

    @Autowired
    public InitialSetup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User normalUser = new User();
        normalUser.setUsername("TestUser");
        normalUser.setPassword("password");
        normalUser.setFirstName("UserFirstName");
        normalUser.setLastName("UserLastName");
        normalUser.setRole("User");

        User adminUser = new User();
        adminUser.setUsername("TestAdmin");
        adminUser.setPassword("password");
        adminUser.setFirstName("AdminFirstName");
        adminUser.setLastName("AdminLastName");
        adminUser.setRole("Admin");

        userRepository.save(normalUser);
        userRepository.save(adminUser);

        System.out.println("Initial users have been created and loaded.");
    }
}
