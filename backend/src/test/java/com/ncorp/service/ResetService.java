package com.ncorp.service;

import com.ncorp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
@Transactional
public class ResetService {

    @Autowired
    private EntityManager entityManager;

    public void resetDatabase(){
        Query query = entityManager.createNativeQuery("delete from user_roles");
        query.executeUpdate();

        deleteEntities(MatchStats.class);
        deleteEntities(User.class);
        deleteEntities(Quiz.class);
        deleteEntities(SubCategory.class);
        deleteEntities(Category.class);
    }

    private void deleteEntities(Class<?> entity){
        if (entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non-entity class");
        }

        String name = entity.getSimpleName();

        Query query = entityManager.createQuery("delete from " + name);
        query.executeUpdate();
    }
}
