package com.example.rinhabackend.service.strategy.impl;

import com.example.rinhabackend.domain.TransacaoRequest;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import java.util.Objects;

public class ValorPositivoRequest implements ValidacaoTransacaoRequest {

    public void validar(TransacaoRequest transacaoRequest) {
        if (Objects.isNull(transacaoRequest.getValor()) || transacaoRequest.getValor() < 0) {
            throw new ValidacaoRequestException(SC_BAD_REQUEST, "Campo 'Valor' nulo ou com valor negativo.");
        }
    }
}