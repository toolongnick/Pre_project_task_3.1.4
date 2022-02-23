package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

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

    @GetMapping("/people/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/people")
    public User saveUser(@RequestBody User user) {
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRole());
        }
        userService.save(user, roles);
        return user;
    }

    @PutMapping("/people")
    public User editUser(@RequestBody User user) {
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRole());
        }
        userService.edit(user, roles);
        return user;
    }

    @DeleteMapping("/people/{id}")
    public void deleteUser (@PathVariable Long id){
        userService.remove(id);
    }
}
