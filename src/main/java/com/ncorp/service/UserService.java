package com.ncorp.service;

import com.ncorp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;

@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUser(String userName, String password){
        String hashedPassword = passwordEncoder.encode(password);

        if (entityManager.find(User.class, userName) != null) return false;

        User user = new User();

        user.setUserName(userName);
        user.setPassword(hashedPassword);
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        entityManager.persist(user);

        return true;
    }
}
