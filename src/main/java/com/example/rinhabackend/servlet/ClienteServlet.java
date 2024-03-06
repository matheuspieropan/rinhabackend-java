package com.example.rinhabackend.servlet;

import com.example.rinhabackend.dto.ExtratoResponse;
import com.example.rinhabackend.dto.TransacaoRequest;
import com.example.rinhabackend.dto.TransacaoResponse;
import com.example.rinhabackend.enums.HttpStatus;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.model.Transacao;
import com.example.rinhabackend.service.ClienteService;
import com.example.rinhabackend.service.ExtratoService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/clientes/*")
public class ClienteServlet extends HttpServlet {

    private final ExtratoService extratoService = new ExtratoService();

    private final ClienteService clienteService = new ClienteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idCliente = obterIdCliente(request.getRequestURI());

            ExtratoResponse extratoResponse = extratoService.obterExtrato(idCliente);
            String saldo = "\"saldo\":" + extratoResponse.getSaldo().toJSON();
            String ultimasTransacoes = "\"ultimas_transacoes\":[" +
                    extratoResponse.getTransacoes().stream()
                            .map(Transacao::toJSON)
                            .collect(Collectors.joining(",")) +
                    "]";

            response.getWriter().write("{" + saldo + "," + ultimasTransacoes + "}");
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

                String novoJson = json.toString().replaceAll("\\s+", "");
                String[] keyValuePairs = novoJson.substring(1, novoJson.length() - 1).split(",");

                TransacaoRequest transacaoRequest = arrayToTransacaoRequest(keyValuePairs);
                TransacaoResponse transacaoResponse = clienteService.transacao(idCliente, transacaoRequest);

                String jsonResponse = "{" +
                        "\"limite\":" + transacaoResponse.getLimite() + "," +
                        "\"saldo\":" + transacaoResponse.getSaldo() + "}";

                response.setStatus(SC_OK);
                response.getWriter().write(jsonResponse);
                response.setContentType("application/json");
            }
        } catch (ValidacaoRequestException ex) {
            response.setStatus(ex.getStatusCode());
        } catch (IOException ex) {
            response.setStatus(SC_BAD_REQUEST);
        }
    }

    private int obterIdCliente(String requestURI) {
        int idCliente = Integer.parseInt(requestURI.split("/")[2]);
        validarCliente(idCliente);

        return idCliente;
    }

    public void validarCliente(int idCliente) {
        if (idCliente < 1 || idCliente > 5) {
            throw new ValidacaoRequestException(HttpStatus.NOT_FOUND.getCodigo(), "Usuário não encontrado.");
        }
    }

    private TransacaoRequest arrayToTransacaoRequest(String[] par) {
        TransacaoRequest transacaoRequest = new TransacaoRequest();

        String valor = par[0].replaceAll("\"", "").split(":")[1];
        Character tipo = par[1].replaceAll("\"", "").split(":")[1].toCharArray()[0];

        String descricao = null;
        boolean possuiDescricao = par[2].replaceAll("\"", "").split(":").length > 1;

        if (possuiDescricao) {
            String descricaoEncontrada = par[2].replaceAll("\"", "").split(":")[1];
            if (!descricaoEncontrada.equalsIgnoreCase("NULL")) {
                descricao = par[2].replaceAll("\"", "").split(":")[1];
            }
        }
        transacaoRequest.setValor(valor);
        transacaoRequest.setTipo(tipo);
        transacaoRequest.setDescricao(descricao);

        return transacaoRequest;
    }
}