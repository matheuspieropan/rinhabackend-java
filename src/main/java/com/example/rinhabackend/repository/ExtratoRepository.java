package com.example.rinhabackend.repository;

import com.example.rinhabackend.conexao.DatabaseConnection;
import com.example.rinhabackend.domain.ExtratoResponse;
import com.example.rinhabackend.entity.Transacao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.rinhabackend.constante.SqlConstante.extratoFindByIdCliente;

public class ExtratoRepository {

    public ExtratoResponse findAll(Long idCliente) {
        PreparedStatement prepareStatement;

        ExtratoResponse extratoResponse = new ExtratoResponse();
        extratoResponse.setSaldo(new ExtratoResponse.Saldo());
        List<Transacao> transacoes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getDataSource().getConnection()) {

            prepareStatement = connection.prepareStatement(extratoFindByIdCliente);
            prepareStatement.setLong(1, idCliente);
            boolean extratoResponseNaoPreenchido = true;

            try (ResultSet rs = prepareStatement.executeQuery()) {
                while (rs.next()) {

                    if(extratoResponseNaoPreenchido){
                        ExtratoResponse.Saldo saldo = new ExtratoResponse.Saldo();

                        saldo.setTotal(rs.getInt("saldo"));
                        saldo.setData(LocalDateTime.now());
                        saldo.setLimite(rs.getInt("limite"));
                        extratoResponse.setSaldo(saldo);
                        extratoResponseNaoPreenchido = false;
                    }

                    if(Objects.isNull(rs.getString("tipo"))){
                        break;
                    }

                    Transacao transacao = new Transacao();
                    transacao.setValor(rs.getInt("valor"));

                    String tipoString = rs.getString("tipo");
                    char tipoChar = tipoString.charAt(0);

                    transacao.setTipo(tipoChar);
                    transacao.setDescricao(rs.getString("descricao"));

                    Timestamp realizadaEm = (Timestamp) rs.getObject("realizada_em");

                    transacao.setRealizadaEm(realizadaEm.toLocalDateTime());
                    transacoes.add(transacao);
                }
            }
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }

        extratoResponse.setTransacoes(transacoes);
        return extratoResponse;
    }
}