package com.example.rinhabackend.service.strategy;

import com.example.rinhabackend.domain.TransacaoRequest;

public interface ValidacaoTransacaoRequest {

    void validar(TransacaoRequest transacaoRequest);
}