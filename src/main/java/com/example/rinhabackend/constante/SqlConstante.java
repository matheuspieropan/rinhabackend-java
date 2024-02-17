package com.example.rinhabackend.constante;

public class SqlConstante {

    public static final String findByIdCliente = "SELECT * FROM cliente c WHERE c.id = ?";

    public static final String updateSaldoCliente = "UPDATE cliente set saldo = ? WHERE id = ?";
}