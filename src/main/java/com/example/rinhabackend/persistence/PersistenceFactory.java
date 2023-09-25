package com.example.rinhabackend.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceFactory {

    private static PersistenceFactory persistenceFactory;

    private static EntityManager entityManager;

    private static EntityManagerFactory entityManagerFactory;

    private PersistenceFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("rinhaBackendPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static EntityManager getInstance() {
        if (persistenceFactory == null) {
            persistenceFactory = new PersistenceFactory();

            return entityManager;
        }

        return entityManager;
    }
}