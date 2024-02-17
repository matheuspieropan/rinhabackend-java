package com.example.rinhabackend.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseConnection {

    private static Connection connection;

    private static DatabaseConnection instance;

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/rinhabackend-java", "postgres", "123");
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    public static synchronized Connection getConnection() {
        if (Objects.isNull(instance)) {
            instance = new DatabaseConnection();
        }
        return connection;
    }
}