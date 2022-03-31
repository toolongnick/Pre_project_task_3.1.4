package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public User findUserByEmail (String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public User save(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }
    @Transactional
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public User edit(User user) {
        if(!user.getPassword().startsWith("$")){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
         return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> listOfUsers() {
        return userRepository.findAll();
    }
}
