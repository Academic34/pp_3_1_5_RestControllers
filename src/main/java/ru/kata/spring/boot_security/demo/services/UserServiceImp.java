package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;

    @Autowired
    public void setUserRepository(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        Set<Role> roles = user.getRoles();
        HashSet<Role> roles2 = new HashSet<>();

        for(Role role:roles){
            roles2.add(roleService.getRoleByName(role.getRole()));
        }
        user.setRoles(roles2);
        userRepository.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
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
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }
}
