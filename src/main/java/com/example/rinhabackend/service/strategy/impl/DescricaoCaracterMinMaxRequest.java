package com.example.rinhabackend.service.strategy.impl;

import com.example.rinhabackend.dto.TransacaoRequest;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;

import java.util.Objects;

import static com.example.rinhabackend.enums.HttpStatus.UNPROCESSABLE_ENTITY;

public class DescricaoCaracterMinMaxRequest implements ValidacaoTransacaoRequest {

    public void validar(TransacaoRequest transacaoRequest) {
        if (Objects.isNull(transacaoRequest.getDescricao())
                || transacaoRequest.getDescricao().isEmpty()
                || transacaoRequest.getDescricao().length() > 10) {
            throw new ValidacaoRequestException(UNPROCESSABLE_ENTITY.getCodigo(), "Operação não permitida. Campo descriço nulo ou com descrição vazia ou com mais de 10 caracteres.");
        }
    }
}