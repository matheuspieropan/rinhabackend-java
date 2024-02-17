package com.example.rinhabackend.servlet;

import com.example.rinhabackend.domain.Cliente;
import com.example.rinhabackend.domain.TransacaoRequest;
import com.example.rinhabackend.enums.HttpStatus;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.repository.ClienteRepository;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;
import com.example.rinhabackend.service.strategy.impl.DebitoNaoPodeSerMenorLimite;
import com.example.rinhabackend.service.strategy.impl.DescricaoCaracterMinMaxRequest;
import com.example.rinhabackend.service.strategy.impl.TipoValidoRequest;
import com.example.rinhabackend.service.strategy.impl.ValorPositivoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/clientes/*")
public class ClienteServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ClienteRepository clienteRepository = new ClienteRepository();

    private final List<ValidacaoTransacaoRequest> validacoes = Arrays.asList(
            new ValorPositivoRequest(),
            new TipoValidoRequest(),
            new DescricaoCaracterMinMaxRequest());

    private final DebitoNaoPodeSerMenorLimite debitoNaoPodeSerMenorLimite = new DebitoNaoPodeSerMenorLimite();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        try {
            Cliente cliente = obterUsuario(request.getRequestURI());

            try (BufferedReader bufferedReader = request.getReader()) {

                StringBuilder json = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    json.append(line);
                }

                TransacaoRequest transacaoRequest = objectMapper.readValue(json.toString(), TransacaoRequest.class);

                realizarValidacoes(transacaoRequest, cliente);

                atualizarSaldo(transacaoRequest, cliente);

                resp.setStatus(SC_OK);
            }
        } catch (ValidacaoRequestException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
        } catch (RuntimeException ex){
            ex.printStackTrace();
        }
    }

    private Cliente obterUsuario(String requestURI) {
        Cliente cliente = clienteRepository.findById(obterIdCliente(requestURI));

        if (Objects.isNull(cliente)) {
            throw new ValidacaoRequestException(HttpStatus.NOT_FOUND.getCodigo(), "Usuário não encontrado.");
        }
        return cliente;
    }

    private Long obterIdCliente(String requestURI) {
        return Long.valueOf(requestURI.split("/")[2]);
    }

    private void realizarValidacoes(TransacaoRequest transacaoRequest,
                                    Cliente cliente) {
        validacoes.forEach(impl -> impl.validar(transacaoRequest));
        debitoNaoPodeSerMenorLimite.validar(transacaoRequest, cliente);
    }

    private void atualizarSaldo(TransacaoRequest transacaoRequest,
                                Cliente cliente) {
        int novoSaldo = cliente.getSaldo() - transacaoRequest.getValor();
        cliente.setSaldo(novoSaldo);
        clienteRepository.atualizaSaldo(novoSaldo, cliente.getId());
    }
}