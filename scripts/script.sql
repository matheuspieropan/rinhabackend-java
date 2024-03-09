CREATE UNLOGGED TABLE cliente
(
    id     INT PRIMARY KEY,
    limite INT NOT NULL,
    saldo  INT NOT NULL
);

INSERT INTO cliente (id, limite, saldo)
VALUES (1, 100000, 0),
       (2, 80000, 0),
       (3, 1000000, 0),
       (4, 10000000, 0),
       (5, 500000, 0);

CREATE UNLOGGED TABLE TRANSACAO
(
    ID           SERIAL      PRIMARY KEY,
    ID_CLIENTE   INT         NOT NULL,
    VALOR        INT         NOT NULL,
    TIPO         VARCHAR(1)  NOT NULL,
    DESCRICAO    VARCHAR(10) NOT NULL,
    REALIZADA_EM TIMESTAMP   NOT NULL
);

CREATE INDEX idx_transacao_id_cliente ON transacao (id_cliente);
CREATE INDEX idx_transacao_id_cliente_realizada_em ON transacao (id_cliente, realizada_em DESC);


CREATE OR REPLACE FUNCTION efetuar_transacao2(
    clienteId int,
    tipoParam varchar(1),
    valorParam int,
    descricaoParam varchar(10)
)
RETURNS TABLE(limiteRetorno int, saldoRetorno int) AS
$$
DECLARE
novoSaldo int;
BEGIN
    IF tipoParam = 'd' THEN
        novoSaldo := valorParam * -1;
ELSE
        novoSaldo := valorParam;
END IF;

UPDATE cliente SET saldo = saldo + novoSaldo WHERE id = clienteId
    RETURNING limite, saldo INTO limiteRetorno, saldoRetorno;

INSERT INTO transacao (id_cliente, valor, tipo, descricao, realizada_em)
VALUES (clienteId, valorParam, tipoParam, descricaoParam, current_timestamp);

RETURN QUERY SELECT limiteRetorno, saldoRetorno;
END;
$$
LANGUAGE plpgsql;