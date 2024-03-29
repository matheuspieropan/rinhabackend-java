package com.example.rinhabackend.conexao;

import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {

    private final static HikariDataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:postgresql://db:5432/rinhabackend-java");
            dataSource.setUsername("postgres");
            dataSource.setPassword("123");
            dataSource.setMaximumPoolSize(10);
            dataSource.setMinimumIdle(5);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}