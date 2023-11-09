package com.example.rinhabackend.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class JDBCTemplate {

    private static Connection connection;

    private static JDBCTemplate JDBCTemplate;

    private JDBCTemplate() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://db:5432/rinhabackend-java",
                    "postgres",
                    "123");
        } catch (ClassNotFoundException | SQLException exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    public static Connection getConnection() {
        if (Objects.isNull(JDBCTemplate)) {
            JDBCTemplate = new JDBCTemplate();
        }
        return connection;
    }
}