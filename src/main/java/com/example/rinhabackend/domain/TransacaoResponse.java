package com.example.rinhabackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TransacaoResponse {

    private int limite;

    private int saldo;
}