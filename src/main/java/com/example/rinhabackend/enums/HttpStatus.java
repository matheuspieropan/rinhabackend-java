package com.example.rinhabackend.enums;

public enum HttpStatus {

    NOT_FOUND(404, "Not Found"),

    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity");

    private int codigo;

    private String descricao;

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    HttpStatus(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}