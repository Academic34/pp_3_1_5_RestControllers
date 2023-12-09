package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
public class UserRepositoryImp implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
//        return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.name = :username", User.class).
//                setParameter("username", username).
//                getSingleResult();

        return (User) entityManager.createQuery("select u from User u left join fetch u.roles where u.name=:username", User.class).
                setParameter("username", username).
                getSingleResult();

//        TypedQuery<User> user = (TypedQuery<User>) entityManager.createQuery(
//                        "select u from User u left join fetch u.roles where u.name=:pname").
//                setParameter("pname", username);
//        return user.getSingleResult();
    }

}
