package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void addUser(User user);

    void updateUser(User user);

    void removeUser(int id);

    User getUserById(int id);
}
