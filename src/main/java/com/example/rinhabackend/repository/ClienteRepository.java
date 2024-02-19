package com.example.rinhabackend.repository;

import com.example.rinhabackend.conexao.DatabaseConnection;
import com.example.rinhabackend.domain.Cliente;
import com.example.rinhabackend.domain.Transacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.example.rinhabackend.constante.SqlConstante.findByIdCliente;
import static com.example.rinhabackend.constante.SqlConstante.atualizaSaldoERegistraTransacao;

public class ClienteRepository {

    public Cliente findById(Long id) {
        Cliente cliente = null;

        try (Connection connection = DatabaseConnection.getDataSource().getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(findByIdCliente);
            prepareStatement.setLong(1, id);

            try (ResultSet rs = prepareStatement.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getLong("id"));
                    cliente.setLimite(rs.getInt("limite"));
                    cliente.setSaldo(rs.getInt("saldo"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cliente;
    }

    public void atualizaSaldoERegistraTransacao(int novoSaldo, Long idCliente, Transacao transacao) {
        try (Connection connection = DatabaseConnection.getDataSource().getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(atualizaSaldoERegistraTransacao);

            prepareStatement.setInt(1, novoSaldo);
            prepareStatement.setLong(2, idCliente);
            prepareStatement.setInt(3, transacao.getValor());
            prepareStatement.setObject(4, transacao.getTipo());
            prepareStatement.setString(5, transacao.getDescricao());
            prepareStatement.setObject(6, LocalDateTime.now());
            prepareStatement.setLong(7, transacao.getIdCliente());

            prepareStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}