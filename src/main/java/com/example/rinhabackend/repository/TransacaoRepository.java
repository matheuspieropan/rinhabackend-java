package com.example.rinhabackend.repository;

import com.example.rinhabackend.conexao.DatabaseConnection;
import com.example.rinhabackend.dto.TransacaoResponse;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.model.Transacao;

import java.sql.*;

import static com.example.rinhabackend.enums.HttpStatus.UNPROCESSABLE_ENTITY;

public class TransacaoRepository {

    public TransacaoResponse atualizaSaldoERegistraTransacao(int idCliente, Transacao transacao) {
        TransacaoResponse transacaoResponse = new TransacaoResponse();
        try (Connection connection = DatabaseConnection.getDataSource().getConnection()) {
            String procedureCall = "{call public.efetuar_transacao(?, ?, ?, ?)}";
            CallableStatement callableStatement = connection.prepareCall(procedureCall);

            callableStatement.setInt(1, idCliente);
            callableStatement.setString(2, String.valueOf(transacao.getTipo()).toLowerCase());
            callableStatement.setInt(3, transacao.getValor());
            callableStatement.setString(4, transacao.getDescricao());

            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                transacaoResponse.setLimite(resultSet.getInt("limiteRetorno"));
                transacaoResponse.setSaldo(resultSet.getInt("saldoRetorno"));
            }
        } catch (SQLException ex) {
            throw new ValidacaoRequestException(UNPROCESSABLE_ENTITY.getCodigo(), "Transação ultrapassa valor limite");
        }
        return transacaoResponse;
    }

    public TransacaoResponse realizarTransacao(int idCliente, char operacao, int valorOperacao, String descricao) {
        TransacaoResponse transacaoResponse = new TransacaoResponse();
        try (Connection connection = DatabaseConnection.getDataSource().getConnection()) {

            connection.setAutoCommit(false);

            String obterSaldo = "SELECT saldo, limite FROM cliente WHERE id = ?";

            PreparedStatement stmSelect = connection.prepareStatement(obterSaldo);
            stmSelect.setInt(1, idCliente);
            ResultSet resultSet = stmSelect.executeQuery();

            if (resultSet.next()) {

                int saldo = resultSet.getInt("saldo");
                int limite = resultSet.getInt("limite");
                boolean possoRealizarOperacao = operacao != 'd' || (saldo - valorOperacao) <= limite;

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
                        transacaoResponse.setLimite(limite);
                        transacaoResponse.setSaldo(novoSaldo);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return transacaoResponse;
    }
}