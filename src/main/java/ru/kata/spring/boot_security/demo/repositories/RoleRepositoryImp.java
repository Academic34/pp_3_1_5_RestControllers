package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImp implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRole() {
        return entityManager.createQuery("from Role", Role.class).getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Role getRoleById(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String name) {
        try {
            return (Role) entityManager.createQuery("from Role r where r.role = :role").
                    setParameter("role", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
