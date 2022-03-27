package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail (String email);
    void save(User newUser);
    void remove(Long id);
    void edit(User user);
    List<User> listOfUsers();
}
