package com.example.rinhabackend.exceptions;

public class ValidacaoRequestException extends RuntimeException {

    private int statusCode;

    private String mensagem;

    public ValidacaoRequestException(int statusCode, String mensagem) {
        this.statusCode = statusCode;
        this.mensagem = mensagem;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}