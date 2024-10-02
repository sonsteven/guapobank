package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.User;

public interface UserService {

    public Iterable<User> findAll();

    public User save(User user);
}
