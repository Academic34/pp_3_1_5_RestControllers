package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;


public interface UserRepository {

    User findByUsername (String username);

    List<User> getAllUsers();

    void addUser(User user);

    void updateUser(User user);

    void removeUser(long id);

    User getUserById(long id);
}
