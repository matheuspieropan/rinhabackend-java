package com.example.rinhabackend.dto;

import com.example.rinhabackend.model.Transacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ExtratoResponse {

    private Saldo saldo;

    @JsonProperty("ultimas_transacoes")
    private List<Transacao> transacoes;

    @Getter
    @Setter
    public static class Saldo {

        private int total;

        @JsonProperty("data_extrato")
        private LocalDateTime data;

        private int limite;
    }
}