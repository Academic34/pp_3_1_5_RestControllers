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
        admin.setFirstName("admin");
        admin.setLastName("admin1");
        admin.setAge(25);
        admin.setEmail("admin@mail.ru");
        admin.setPassword("100");
        admin.addRole(new Role("ROLE_ADMIN"));
        userService.addUser(admin);

        User user = new User();
        user.setFirstName("user");
        user.setLastName("user1");
        user.setAge(25);
        user.setEmail("user@mail.ru");
        user.setPassword("100");
        user.addRole(new Role("ROLE_USER"));
        userService.addUser(user);
    }
}
