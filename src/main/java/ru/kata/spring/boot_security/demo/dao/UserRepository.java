package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.roles")
    User findUserByEmail(String email);

    @EntityGraph(value = "User.roles")
    @Override
    <S extends User> List<S> findAll(Example<S> example);
}
