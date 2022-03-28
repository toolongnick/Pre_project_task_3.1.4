package ru.kata.spring.boot_security.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    ResponseEntity<UserDetails> findUserByEmail (String email);
    ResponseEntity<User> save(User newUser);
    HttpStatus remove(Long id);
    ResponseEntity<User> edit(User user);
    List<User> listOfUsers();
}
