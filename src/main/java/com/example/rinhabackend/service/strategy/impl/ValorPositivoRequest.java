package com.example.rinhabackend.service.strategy.impl;

import com.example.rinhabackend.domain.TransacaoRequest;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;

import static com.example.rinhabackend.enums.HttpStatus.UNPROCESSABLE_ENTITY;

public class ValorPositivoRequest implements ValidacaoTransacaoRequest {

    public void validar(TransacaoRequest transacaoRequest) {
        try {
            Integer.parseInt(transacaoRequest.getValor());
        } catch (Exception ex) {
            throw new ValidacaoRequestException(UNPROCESSABLE_ENTITY.getCodigo(), "Campo 'Valor' nulo ou com valor negativo.");
        }
    }
}