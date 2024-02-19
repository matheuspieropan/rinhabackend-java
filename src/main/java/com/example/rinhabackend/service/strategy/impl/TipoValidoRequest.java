package com.example.rinhabackend.service.strategy.impl;

import com.example.rinhabackend.domain.TransacaoRequest;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;

import java.util.Objects;

import static com.example.rinhabackend.enums.HttpStatus.UNPROCESSABLE_ENTITY;

public class TipoValidoRequest implements ValidacaoTransacaoRequest {

    public void validar(TransacaoRequest transacaoRequest) {
        char debito = 'D';
        char credito = 'C';

        if (Objects.isNull(transacaoRequest.getTipo()) || (Character.toUpperCase(transacaoRequest.getTipo()) !=
                debito && Character.toUpperCase(transacaoRequest.getTipo()) != credito)) {
            throw new ValidacaoRequestException(UNPROCESSABLE_ENTITY.getCodigo(), "Campo 'Tipo' nulo ou com valor diferente do permitido.");
        }
    }
}