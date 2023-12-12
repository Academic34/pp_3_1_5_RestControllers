package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRole();

    Role getRoleById(long id);

    Role getRoleByName(String name);
}
