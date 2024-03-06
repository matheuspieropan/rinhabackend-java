package com.example.rinhabackend.model;

import java.sql.Timestamp;

public class Transacao {

    private int valor;

    private char tipo;

    private String descricao;

    private Timestamp realizadaEm;

    private int idCliente;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setRealizadaEm(Timestamp realizadaEm) {
        this.realizadaEm = realizadaEm;
    }

    public Transacao(int valor, char tipo, String descricao, int idCliente) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.idCliente = idCliente;
    }

    public Transacao() {
    }

    public String toJSON() {
        return "{" +
                "\"valor\":" + valor + "," +
                "\"tipo\":\"" + tipo + "\"," +
                "\"descricao\":\"" + descricao + "\"," +
                "\"realizada_em\":\"" + realizadaEm + "\"" +
                "}";
    }
}