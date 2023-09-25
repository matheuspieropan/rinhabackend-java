package com.example.rinhabackend.repository;

import com.example.rinhabackend.persistence.PersistenceFactory;
import jakarta.persistence.EntityManager;

public class CrudRepository<T> {

    private final EntityManager entityManager = PersistenceFactory.getInstance();

    public void salvar(T entity) {
        entityManager.getTransaction().begin();

        entityManager.persist(entity);

        entityManager.getTransaction().commit();
    }
}