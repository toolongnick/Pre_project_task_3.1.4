package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail (String email);
    User save(User newUser);
    void remove(Long id);
    User edit(User user);
    List<User> listOfUsers();
}
