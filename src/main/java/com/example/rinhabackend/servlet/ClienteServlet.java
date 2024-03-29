package com.example.rinhabackend.servlet;

import com.example.rinhabackend.dto.TransacaoRequest;
import com.example.rinhabackend.dto.TransacaoResponse;
import com.example.rinhabackend.enums.HttpStatus;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.service.ClienteService;
import com.example.rinhabackend.service.ExtratoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/clientes/*")
public class ClienteServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ExtratoService extratoService = new ExtratoService();

    private final ClienteService clienteService = new ClienteService();

    public ClienteServlet() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idCliente = obterIdCliente(request.getRequestURI());

            response.getWriter().write(objectMapper.writeValueAsString(extratoService.obterExtrato(idCliente)));
            response.setContentType("application/json");

        } catch (ValidacaoRequestException ex) {
            response.setStatus(ex.getStatusCode());
        } catch (IOException ex) {
            response.setStatus(SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idCliente = obterIdCliente(request.getRequestURI());

            try (BufferedReader bufferedReader = new BufferedReader(request.getReader())) {

                StringBuilder json = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    json.append(line);
                }

                TransacaoRequest transacaoRequest = objectMapper.readValue(json.toString(), TransacaoRequest.class);
                TransacaoResponse transacaoResponse = clienteService.transacao(idCliente, transacaoRequest);

                response.setStatus(SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(transacaoResponse));
                response.setContentType("application/json");
            }
        } catch (ValidacaoRequestException ex) {
            response.setStatus(ex.getStatusCode());
        } catch (IOException ex) {
            response.setStatus(SC_BAD_REQUEST);
        }
    }

    private int obterIdCliente(String requestURI) {
        int idCliente = Integer.valueOf(requestURI.split("/")[2]);
        validarCliente(idCliente);

        return idCliente;
    }

    public void validarCliente(int idCliente) {
        if (idCliente < 1 || idCliente > 5) {
            throw new ValidacaoRequestException(HttpStatus.NOT_FOUND.getCodigo(), "Usuário não encontrado.");
        }
    }
}