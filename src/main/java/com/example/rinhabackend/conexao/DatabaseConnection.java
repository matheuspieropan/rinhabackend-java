package com.example.rinhabackend.conexao;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnection {

    private final static BasicDataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            dataSource = new BasicDataSource();
            dataSource.setUrl("jdbc:postgresql://localhost:5432/rinhabackend-java");
            dataSource.setUsername("postgres");
            dataSource.setPassword("123");
            dataSource.setMaxTotal(10);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }
}