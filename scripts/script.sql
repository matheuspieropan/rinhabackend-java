CREATE TABLE cliente
(
    id     SERIAL PRIMARY KEY,
    limite INT NOT NULL,
    saldo  INT NOT NULL
);

INSERT INTO cliente (limite, saldo)
VALUES (100000, 0),
       (80000, 0),
       (1000000, 0),
       (10000000, 0),
       (500000, 0);

CREATE TABLE TRANSACAO
(
    ID           SERIAL PRIMARY KEY,
    ID_CLIENTE   INT         NOT NULL,
    VALOR        INT         NOT NULL,
    TIPO         CHAR(1)     NOT NULL,
    DESCRICAO    VARCHAR(10) NOT NULL,
    REALIZADA_EM TIMESTAMP   NOT NULL
)