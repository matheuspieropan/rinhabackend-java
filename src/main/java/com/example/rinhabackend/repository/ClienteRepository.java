package com.example.rinhabackend.repository;

import com.example.rinhabackend.conexao.DatabaseConnection;
import com.example.rinhabackend.domain.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.rinhabackend.constante.SqlConstante.findByIdCliente;
import static com.example.rinhabackend.constante.SqlConstante.updateSaldoCliente;

public class ClienteRepository {

    public Cliente findById(Long id) {
        Cliente cliente = null;
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement prepareStatement;
        try {
            prepareStatement = connection.prepareStatement(findByIdCliente);
            prepareStatement.setLong(1, id);

            try (ResultSet rs = prepareStatement.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getLong("id"));
                    cliente.setLimite(rs.getInt("limite"));
                    cliente.setSaldo(rs.getInt("saldo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return cliente;
    }

    public void atualizaSaldo(int novoSaldo, Long id) {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement prepareStatement;

        try {
            prepareStatement = connection.prepareStatement(updateSaldoCliente);

            prepareStatement.setInt(1, novoSaldo);
            prepareStatement.setLong(2, id);

            prepareStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}