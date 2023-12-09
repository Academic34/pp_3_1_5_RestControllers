package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return null;
//        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void addUser(User user) {
//        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
//        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
//        userDao.removeUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return null;
//        return userDao.getUserById(id);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }
}
