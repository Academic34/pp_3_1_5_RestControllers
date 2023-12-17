package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;

@Component
public class Init {

    private UserService userService;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserService(UserService userService, PasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void runAfterStartup() {
        User admin = new User();
        admin.setName("admin");
        admin.setYearOfBirth(28);
        admin.setPassword("100");
        admin.addRole(new Role("ROLE_ADMIN"));
        userService.addUser(admin);

        User user = new User();
        user.setName("user");
        user.setYearOfBirth(18);
        user.setPassword("100");
        user.addRole(new Role("ROLE_USER"));
        userService.addUser(user);
    }
}
