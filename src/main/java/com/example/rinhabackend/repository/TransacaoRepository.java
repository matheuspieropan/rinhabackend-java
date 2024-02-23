package com.example.rinhabackend.repository;

import com.example.rinhabackend.conexao.DatabaseConnection;
import com.example.rinhabackend.dto.TransacaoResponse;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.model.Transacao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}