package com.example.rinhabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transacao {

    private int valor;

    private char tipo;

    private String descricao;

    @JsonProperty("realizada_em")
    private Timestamp realizadaEm;

    @JsonIgnore
    private Long idCliente;
}