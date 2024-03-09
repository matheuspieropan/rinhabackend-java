package com.example.rinhabackend.repository;

import com.example.rinhabackend.conexao.DatabaseConnection;
import com.example.rinhabackend.dto.TransacaoResponse;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.model.Transacao;

import java.sql.*;

import static com.example.rinhabackend.enums.HttpStatus.UNPROCESSABLE_ENTITY;

public class TransacaoRepository {

    public synchronized TransacaoResponse atualizaSaldoERegistraTransacao(int idCliente,
                                                                          Transacao transacao,
                                                                          int limite) {
        TransacaoResponse transacaoResponse = new TransacaoResponse();

        try (Connection connection = DatabaseConnection.getDataSource().getConnection()) {

            if (transacao.getTipo() == 'd') {

                PreparedStatement pstm = connection.prepareStatement("SELECT saldo FROM cliente WHERE id = ?");
                pstm.setInt(1, idCliente);
                ResultSet rs = pstm.executeQuery();

                if (rs.next()) {
                    int novoSaldo = Math.abs((rs.getInt("saldo") - transacao.getValor()));
                    if (novoSaldo > limite) {
                        throw new ValidacaoRequestException(UNPROCESSABLE_ENTITY.getCodigo(), "Transação ultrapassa valor limite");
                    }
                }
            }

            String procedureCall = "{call public.efetuar_transacao2(?, ?, ?, ?)}";
            CallableStatement callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1, idCliente);
            callableStatement.setString(2, String.valueOf(transacao.getTipo()).toLowerCase());
            callableStatement.setInt(3, transacao.getValor());
            callableStatement.setString(4, transacao.getDescricao());

            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                transacaoResponse.setSaldo(resultSet.getInt("saldoRetorno"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return transacaoResponse;
    }
}