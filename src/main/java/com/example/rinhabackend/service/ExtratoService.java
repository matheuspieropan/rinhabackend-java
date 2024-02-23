package com.example.rinhabackend.service;

import com.example.rinhabackend.dto.ExtratoResponse;
import com.example.rinhabackend.repository.ExtratoRepository;

public class ExtratoService {

    private final ExtratoRepository extratoRepository = new ExtratoRepository();

    public ExtratoResponse obterExtrato(int idCliente) {
        return extratoRepository.findAllById(idCliente);
    }
}