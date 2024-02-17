package com.example.rinhabackend.constante;

public class SqlConstante {

    public static final String findByIdCliente = "SELECT * FROM cliente c WHERE c.id = ?";

    public static final String updateSaldoCliente = "UPDATE cliente set saldo = ? WHERE id = ?";

    public static final String saveTransacao = "INSERT INTO transacao(valor, tipo, descricao, realizada_em, id_cliente) VALUES (?, ?, ?, ?, ?)";

    public static final String extratoFindByIdCliente = "SELECT t.valor, t.tipo, t.descricao, t.realizada_em FROM transacao t WHERE t.id = ? ORDER BY t.id DESC LIMIT 10";
}