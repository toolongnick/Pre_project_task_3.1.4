package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
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
    public ResponseEntity<List<User>>  getAllUsers() {
        return ResponseEntity.ok().body(userService.listOfUsers());
    }

    @GetMapping("/people/loggedUser")
    public ResponseEntity<UserDetails> getUser(Principal principal) {
        return userService.findUserByEmail(principal.getName());
    }

    @PostMapping("/people")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/people")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        return userService.edit(user);
    }

    @DeleteMapping("/people/{id}")
    public HttpStatus deleteUser (@PathVariable Long id){
        return userService.remove(id);
    }
}
