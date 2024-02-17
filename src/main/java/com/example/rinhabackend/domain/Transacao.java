package com.example.rinhabackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transacao {

    private int valor;

    private char tipo;

    private String descricao;

    @JsonProperty("realizada_em")
    private LocalDateTime realizadaEm;

    @JsonIgnore
    private Long idCliente;
}