package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @GetMapping("/people")
    public List<User> getAllUsers() {
        return userService.listOfUsers();
    }

    @GetMapping("/people/loggedUser")
    public User getUser(Principal principal) {
        return userService.findUserByEmail(principal.getName());
    }

    @PostMapping("/people")
    public List<User> saveUser(@RequestBody User user) {
        userService.save(user, null);
        return userService.listOfUsers();
    }

    @PutMapping("/people")
    public List<User> editUser(@RequestBody User user) {
        userService.edit(user, null);
        return userService.listOfUsers();
    }

    @DeleteMapping("/people/{id}")
    public List<User> deleteUser (@PathVariable Long id){
        userService.remove(id);
        return userService.listOfUsers();
    }
}
