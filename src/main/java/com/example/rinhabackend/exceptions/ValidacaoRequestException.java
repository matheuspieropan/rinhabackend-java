package com.example.rinhabackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidacaoRequestException extends RuntimeException {

    private int statusCode;

    private String mensagem;
}