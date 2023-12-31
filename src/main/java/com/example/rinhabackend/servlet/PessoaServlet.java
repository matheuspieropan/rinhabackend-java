package com.example.rinhabackend.servlet;

import com.example.rinhabackend.entity.Pessoa;
import com.example.rinhabackend.repository.PessoaRepository;
import com.example.rinhabackend.util.ResponseHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet
public class PessoaServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final PessoaRepository repository = new PessoaRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try (BufferedReader bufferedReader = request.getReader()) {

            StringBuilder json = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                json.append(line);
            }

            Pessoa pessoa = objectMapper.readValue(json.toString(), Pessoa.class);
            repository.salvar(pessoa);

            ResponseHttpUtil.createdSucess(response);
        } catch (IOException ex) {
            ResponseHttpUtil.failed(response);
        }
    }
}