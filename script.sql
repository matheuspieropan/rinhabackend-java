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