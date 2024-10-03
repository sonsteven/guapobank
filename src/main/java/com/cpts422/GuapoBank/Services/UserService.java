package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.User;

import java.util.Optional;

public interface UserService {

    public Iterable<User> findAll();

    public User save(User user);

    public User authenticate(String username, String password);

    public Optional<User> findById(Long id);
}
