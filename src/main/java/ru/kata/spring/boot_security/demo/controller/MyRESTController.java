package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    @Autowired
    public void setUserService(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    private UserServiceImpl userService;

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
        userService.save(user);
        return userService.listOfUsers();
    }

    @PutMapping("/people")
    public List<User> editUser(@RequestBody User user) {
        userService.edit(user);
        return userService.listOfUsers();
    }

    @DeleteMapping("/people/{id}")
    public List<User> deleteUser (@PathVariable Long id){
        userService.remove(id);
        return userService.listOfUsers();
    }
}
