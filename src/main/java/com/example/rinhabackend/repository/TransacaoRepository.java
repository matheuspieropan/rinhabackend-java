package com.example.rinhabackend.repository;

import com.example.rinhabackend.conexao.DatabaseConnection;
import com.example.rinhabackend.dto.TransacaoResponse;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoRepository {

    public TransacaoResponse realizarTransacao(int idCliente, char operacao, int valorOperacao, String descricao) {
        try (Connection connection = DatabaseConnection.getDataSource().getConnection()) {

            connection.setAutoCommit(false);

            String obterSaldo = "SELECT saldo, limite FROM cliente WHERE id = ? FOR UPDATE;";

            PreparedStatement stmSelect = connection.prepareStatement(obterSaldo);
            stmSelect.setInt(1, idCliente);
            ResultSet resultSet = stmSelect.executeQuery();

            if (resultSet.next()) {

                int saldo = resultSet.getInt("saldo");
                int limite = resultSet.getInt("limite");
                boolean possoRealizarOperacao = operacao != 'd' || Math.abs(saldo - valorOperacao) <= limite;

                if (possoRealizarOperacao) {

                    String updateSaldo = "update cliente set saldo = ? WHERE id = ?";
                    int novoSaldo = operacao == 'd' ? (saldo - valorOperacao) : (saldo + valorOperacao);
                    try (PreparedStatement update = connection.prepareStatement(updateSaldo)) {
                        update.setInt(1, novoSaldo);
                        update.setInt(2, idCliente);
                        update.executeUpdate();
                    }

                    String insertTransacao = "INSERT INTO transacao (id_cliente, valor, tipo, descricao, realizada_em) " +
                            "VALUES (?, ?, ?, ?, current_timestamp)";

                    try (PreparedStatement stmInsert = connection.prepareStatement(insertTransacao)) {
                        stmInsert.setInt(1, idCliente);
                        stmInsert.setInt(2, valorOperacao);
                        stmInsert.setObject(3, operacao);
                        stmInsert.setString(4, descricao);

                        stmInsert.executeUpdate();
                        connection.commit();

                        return new TransacaoResponse(limite, novoSaldo);
                    }
                } else {
                    throw new ValidacaoRequestException(422, "Não pode realizar transação!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}