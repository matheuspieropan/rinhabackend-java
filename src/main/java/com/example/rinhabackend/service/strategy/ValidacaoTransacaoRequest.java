package com.example.rinhabackend.service.strategy;

import com.example.rinhabackend.dto.TransacaoRequest;

public interface ValidacaoTransacaoRequest {

    void validar(TransacaoRequest transacaoRequest);
}