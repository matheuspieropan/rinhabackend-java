package com.example.rinhabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    private Long id;

    private String nome;

    private String apelido;

    private String nascimento;

    private List<String> stack;
}