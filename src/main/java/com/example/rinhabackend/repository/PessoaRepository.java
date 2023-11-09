package com.example.rinhabackend.repository;

import com.example.rinhabackend.entity.JDBCTemplate;
import com.example.rinhabackend.entity.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PessoaRepository {

    private final String insert = "insert into pessoa(nome, apelido, nascimento, stack) values (?, ?, ?, ?)";

    public void salvar(Pessoa pessoa) {
        Connection connection = JDBCTemplate.getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(insert);
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getApelido());
            statement.setString(3, pessoa.getNascimento());
            statement.setString(4, pessoa.getStack().toString());

            statement.execute();

        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}