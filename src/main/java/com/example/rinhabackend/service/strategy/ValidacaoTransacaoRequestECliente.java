package com.example.rinhabackend.service.strategy;

import com.example.rinhabackend.domain.TransacaoRequest;
import com.example.rinhabackend.domain.Cliente;

public interface ValidacaoTransacaoRequestECliente {

    void validar(TransacaoRequest transacaoRequest, Cliente cliente);
}