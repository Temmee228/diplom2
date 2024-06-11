package com.example.diplom2.service;

import com.example.diplom2.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    void save(User user);
}
