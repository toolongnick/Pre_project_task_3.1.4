package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>>  getAllUsers() {
        return ResponseEntity.ok().body(userService.listOfUsers());
    }

    @GetMapping("/people/loggedUser")
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(userService.findUserByEmail(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/people")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user),HttpStatus.OK);
    }

    @PutMapping("/people")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.edit(user), HttpStatus.OK);
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<HttpStatus> deleteUser (@PathVariable Long id){
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
