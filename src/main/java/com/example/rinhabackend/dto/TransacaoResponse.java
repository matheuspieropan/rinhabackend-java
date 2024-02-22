package com.example.rinhabackend.dto;


public class TransacaoResponse {

    private int limite;

    private int saldo;

    public TransacaoResponse(int limite, int saldo) {
        this.limite = limite;
        this.saldo = saldo;
    }

    public TransacaoResponse() {
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}