package com.example.rinhabackend.service.strategy.impl;

import com.example.rinhabackend.domain.TransacaoRequest;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;

import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class DescricaoCaracterMinMaxRequest implements ValidacaoTransacaoRequest {

    public void validar(TransacaoRequest transacaoRequest) {
        if (Objects.isNull(transacaoRequest.getDescricao())
                || transacaoRequest.getDescricao().isEmpty()
                || transacaoRequest.getDescricao().length() > 10) {
            throw new ValidacaoRequestException(SC_BAD_REQUEST, "Operação não permitida. Campo descriço nulo ou com descrição vazia ou com mais de 10 caracteres.");
        }
    }
}