package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<UserDetails> findUserByEmail (String email) {
        return ResponseEntity.ok().body(userRepository.findUserByEmail(email));
    }

    @Transactional
    public ResponseEntity<User> save(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        if (newUser.getRoles().size() == 0) {
            newUser.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.CREATED) ;
    }

    public HttpStatus remove(Long id) {
        userRepository.deleteById(id);
        for (User user : userRepository.findAll()) {
            if(user.getId().equals(id)) {
                return  HttpStatus.NOT_IMPLEMENTED;
            }
        }
        return HttpStatus.OK;
    }

    public ResponseEntity<User> edit(User user) {
        if(!user.getPassword().startsWith("$")){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles().size() == 0) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
         return ResponseEntity.ok().body(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<User> listOfUsers() {
        return userRepository.findAll();
    }
}
