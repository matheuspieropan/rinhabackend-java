package com.example.rinhabackend.constante;

public class SqlConstante {

    public static final String findByIdCliente = "SELECT * FROM cliente c WHERE c.id = ?";

    public static final String atualizaSaldoERegistraTransacao = """
                    BEGIN;
                    UPDATE cliente SET saldo = ? WHERE id = ?;
                    INSERT INTO transacao(valor, tipo, descricao, realizada_em, id_cliente) VALUES (?, ?, ?, ?, ?);
                    COMMIT;
            """;

    public static final String saveTransacao = "INSERT INTO transacao(valor, tipo, descricao, realizada_em, id_cliente) VALUES (?, ?, ?, ?, ?)";

    public static final String extratoFindByIdCliente = "select c.saldo, c.limite, t.valor, t.tipo, t.descricao, t.realizada_em from transacao t join cliente c ON c.id = t.id_cliente where t.id_cliente = ? order by t.id asc limit 10";
}