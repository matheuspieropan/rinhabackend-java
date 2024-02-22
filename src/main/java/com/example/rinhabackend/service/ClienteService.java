package com.example.rinhabackend.service;

import com.example.rinhabackend.dto.TransacaoRequest;
import com.example.rinhabackend.dto.TransacaoResponse;
import com.example.rinhabackend.model.Transacao;
import com.example.rinhabackend.repository.TransacaoRepository;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;
import com.example.rinhabackend.service.strategy.impl.DescricaoCaracterMinMaxRequest;
import com.example.rinhabackend.service.strategy.impl.TipoValidoRequest;
import com.example.rinhabackend.service.strategy.impl.ValorPositivoRequest;

import java.util.Arrays;
import java.util.List;

public class ClienteService {

    private final TransacaoRepository transacaoRepository = new TransacaoRepository();

    private final List<ValidacaoTransacaoRequest> validacoesDadosRequisicao = Arrays.asList(
            new ValorPositivoRequest(),
            new TipoValidoRequest(),
            new DescricaoCaracterMinMaxRequest());

    public TransacaoResponse transacao(Long idCliente, TransacaoRequest transacaoRequest) {
        validarDadosRequisicao(transacaoRequest);
        return realizarTransacao(transacaoRequest, idCliente);
    }

    private void validarDadosRequisicao(TransacaoRequest transacaoRequest) {
        validacoesDadosRequisicao.forEach(impl -> impl.validar(transacaoRequest));
    }

    private TransacaoResponse realizarTransacao(TransacaoRequest transacaoRequest, Long idCliente) {
        Transacao transacao = new Transacao(Integer.parseInt(transacaoRequest.getValor()), transacaoRequest.getTipo(), transacaoRequest.getDescricao(), idCliente);

        return transacaoRepository.atualizaSaldoERegistraTransacao(idCliente, transacao);
    }
}