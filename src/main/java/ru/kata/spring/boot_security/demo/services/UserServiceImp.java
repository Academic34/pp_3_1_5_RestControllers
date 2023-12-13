package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository, RoleService roleService, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void runAfterStartup() {
        User admin = new User();
        admin.setName("admin");
        admin.setYearOfBirth(28);
        admin.setPassword(bCryptPasswordEncoder.encode("100"));
        admin.addRole(new Role("ROLE_ADMIN"));
        userRepository.addUser(admin);

        User user = new User();
        user.setName("user");
        user.setYearOfBirth(18);
        user.setPassword(bCryptPasswordEncoder.encode("100"));
        user.addRole(new Role("ROLE_USER"));
        userRepository.addUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        HashSet<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.getRoleByName(role.getRole()));
        }
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        HashSet<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.getRoleByName(role.getRole()));
        }
        user.setRoles(roles);
        userRepository.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        userRepository.removeUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }
}
