package com.example.rinhabackend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoRequest {

    private Integer valor;

    private Character tipo;

    private String descricao;
}