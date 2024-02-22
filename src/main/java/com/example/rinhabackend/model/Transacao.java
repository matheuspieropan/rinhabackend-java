package com.example.rinhabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Transacao {

    private int valor;

    private char tipo;

    private String descricao;

    @JsonProperty("realizada_em")
    private Timestamp realizadaEm;

    @JsonIgnore
    private Long idCliente;

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

    public Transacao(int valor, char tipo, String descricao, Long idCliente) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.idCliente = idCliente;
    }

    public Transacao() {
    }
}