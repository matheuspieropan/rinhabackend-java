package com.example.rinhabackend.dto;

import com.example.rinhabackend.model.Transacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class ExtratoResponse {

    private Saldo saldo;

    @JsonProperty("ultimas_transacoes")
    private List<Transacao> transacoes;

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public static class Saldo {

        private int total;

        @JsonProperty("data_extrato")
        private LocalDateTime data;

        private int limite;

        public void setTotal(int total) {
            this.total = total;
        }

        public void setData(LocalDateTime data) {
            this.data = data;
        }

        public void setLimite(int limite) {
            this.limite = limite;
        }

        public int getTotal() {
            return total;
        }

        public LocalDateTime getData() {
            return data;
        }

        public int getLimite() {
            return limite;
        }
    }
}