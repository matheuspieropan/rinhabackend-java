package com.example.rinhabackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoRequest {

    private String valor;

    private Character tipo;

    private String descricao;
}