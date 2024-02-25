package com.example.rinhabackend.service;

import com.example.rinhabackend.dto.TransacaoRequest;
import com.example.rinhabackend.dto.TransacaoResponse;
import com.example.rinhabackend.repository.TransacaoRepository;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;
import com.example.rinhabackend.service.strategy.impl.DescricaoCaracterMinMaxRequest;
import com.example.rinhabackend.service.strategy.impl.TipoValidoRequest;
import com.example.rinhabackend.service.strategy.impl.ValorPositivoRequest;

import java.util.Arrays;
import java.util.List;

public class TransacaoService {

    private final TransacaoRepository transacaoRepository = new TransacaoRepository();

    private final List<ValidacaoTransacaoRequest> validacoesDadosRequisicao = Arrays.asList(
            new ValorPositivoRequest(),
            new TipoValidoRequest(),
            new DescricaoCaracterMinMaxRequest());

    public TransacaoResponse realizarTransacao(int idCliente, TransacaoRequest transacaoRequest) {
        validarDadosRequisicao(transacaoRequest);
        return transacaoRepository.realizarTransacao(idCliente, transacaoRequest.getTipo(), Integer.parseInt(transacaoRequest.getValor()), transacaoRequest.getDescricao());
    }

    private void validarDadosRequisicao(TransacaoRequest transacaoRequest) {
        validacoesDadosRequisicao.forEach(impl -> impl.validar(transacaoRequest));
    }
}