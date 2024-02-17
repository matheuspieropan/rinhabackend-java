package com.example.rinhabackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpStatus {

    NOT_FOUND(404, "Not Found"),

    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity");

    private int codigo;

    private String descricao;
}