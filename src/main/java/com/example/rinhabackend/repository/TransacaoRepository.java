package com.example.rinhabackend.repository;

import com.example.rinhabackend.conexao.DatabaseConnection;
import com.example.rinhabackend.domain.ExtratoResponse;
import com.example.rinhabackend.domain.Transacao;
import com.example.rinhabackend.domain.TransacaoResponse;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.rinhabackend.constante.SqlConstante.extratoFindByIdCliente;
import static com.example.rinhabackend.constante.SqlConstante.saveTransacao;

public class TransacaoRepository {

    public void save(Transacao transacao) {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement prepareStatement;

        try {
            prepareStatement = connection.prepareStatement(saveTransacao);

            prepareStatement.setInt(1, transacao.getValor());
            prepareStatement.setObject(2, transacao.getTipo());
            prepareStatement.setString(3, transacao.getDescricao());
            prepareStatement.setObject(4, LocalDateTime.now());
            prepareStatement.setLong(5, transacao.getIdCliente());

            prepareStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ExtratoResponse findAll(Long idCliente) {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement prepareStatement;

        ExtratoResponse extratoResponse = new ExtratoResponse();
        List<Transacao> transacoes = new ArrayList<>();

        try {
            prepareStatement = connection.prepareStatement(extratoFindByIdCliente);

            prepareStatement.setLong(1, idCliente);

            try (ResultSet rs = prepareStatement.executeQuery()) {
                while (rs.next()) {

                    if (Objects.isNull(extratoResponse.getSaldo())) {
                        ExtratoResponse.Saldo saldo = new ExtratoResponse.Saldo();

                        saldo.setTotal(rs.getInt("saldo"));
                        saldo.setData(LocalDateTime.now());
                        saldo.setLimite(rs.getInt("limite"));

                        extratoResponse.setSaldo(saldo);
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        extratoResponse.setTransacoes(transacoes);
        return extratoResponse;
    }
}