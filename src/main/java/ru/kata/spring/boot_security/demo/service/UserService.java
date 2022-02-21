package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;

@Service
public class UserService  {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService (UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public User findUserByEmail (String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public void save(User newUser, ArrayList<String> roles) {
        setUserRoles(roles, newUser);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        if (newUser.getRoles() == null) {
            newUser.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        userRepository.save(newUser);
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public void edit(ArrayList<String> roles, String firstName, String lastName, Integer age, String email, String password, Long id) {
        User editUser = userRepository.getById(id);
        editUser.setFirstName(firstName);
        editUser.setSurname(lastName);
        editUser.setAge(age);
        editUser.setEmail(email);
        editUser.setPassword(password);
        setUserRoles(roles, editUser);
        editUser.setPassword(bCryptPasswordEncoder.encode(password));
        if (editUser.getRoles() == null) {
            editUser.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        userRepository.save(editUser);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<User> listOfUsers() {
        return userRepository.findAll();
    }


    private void setUserRoles(@RequestParam("EditListRoles") ArrayList<String> roles, User editUser) {
        Set<Role> editRoles = new HashSet<>();
        for (String role : roles) {
            editRoles.add(new Role(role.equals("ROLE_USER") ? 1L : 2L, role.equals("ROLE_USER") ? "ROLE_USER" : "ROLE_ADMIN"));
        }
        editUser.setRoles(editRoles);
    }
}
