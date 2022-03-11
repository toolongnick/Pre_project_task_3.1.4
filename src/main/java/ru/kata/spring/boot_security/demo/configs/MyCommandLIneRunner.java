package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


@Component
public class MyCommandLIneRunner implements CommandLineRunner {

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) {
/*        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode("1user"));
        user.setFirstName("Александр");
        user.setSurname("Сергеев");
        user.setEmail("1user@mail.com");
        user.setAge(3);
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        userRepository.save(user);

        User administrator = new User();
        administrator.setPassword(bCryptPasswordEncoder.encode("1admin"));
        administrator.setFirstName("Иван");
        administrator.setSurname("Леванов");
        administrator.setAge(43);
        administrator.setEmail("1admin@mail.com");
        administrator.setRoles(new HashSet<>(Arrays.asList(new Role(1L, "ROLE_USER"), new Role(2L, "ROLE_ADMIN"))));
        userRepository.save(administrator);*/
    }
}
