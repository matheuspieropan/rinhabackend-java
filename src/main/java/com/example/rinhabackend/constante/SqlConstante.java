package com.example.rinhabackend.constante;

public class SqlConstante {

    public static final String extratoFindByIdCliente = "SELECT c.saldo, c.limite, t.valor, t.tipo, t.descricao, t.realizada_em from cliente c left join transacao t " +
            "ON t.id_cliente = c.id WHERE c.id = ? order by t.realizada_em DESC limit 10";
}