package com.example.rinhabackend.service.strategy.impl;

import com.example.rinhabackend.domain.TransacaoRequest;
import com.example.rinhabackend.domain.Cliente;
import com.example.rinhabackend.enums.HttpStatus;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequestECliente;

public class DebitoNaoPodeSerMenorLimite implements ValidacaoTransacaoRequestECliente {

    @Override
    public void validar(TransacaoRequest transacaoRequest, Cliente cliente) {
        if (Character.toUpperCase(transacaoRequest.getTipo()) == 'D') {
            int novoSaldo = cliente.getSaldo() - transacaoRequest.getValor();

            if ((cliente.getLimite() + novoSaldo) < 0) {
                throw new ValidacaoRequestException(HttpStatus.UNPROCESSABLE_ENTITY.getCodigo(), "Transação ultrapassa valor limite");
            }
        }
    }
}