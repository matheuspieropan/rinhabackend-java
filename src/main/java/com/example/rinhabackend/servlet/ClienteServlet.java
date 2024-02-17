package com.example.rinhabackend.servlet;

import com.example.rinhabackend.domain.Cliente;
import com.example.rinhabackend.domain.Transacao;
import com.example.rinhabackend.domain.TransacaoRequest;
import com.example.rinhabackend.enums.HttpStatus;
import com.example.rinhabackend.exceptions.ValidacaoRequestException;
import com.example.rinhabackend.repository.ClienteRepository;
import com.example.rinhabackend.repository.TransacaoRepository;
import com.example.rinhabackend.service.strategy.ValidacaoTransacaoRequest;
import com.example.rinhabackend.service.strategy.impl.DebitoNaoPodeSerMenorLimite;
import com.example.rinhabackend.service.strategy.impl.DescricaoCaracterMinMaxRequest;
import com.example.rinhabackend.service.strategy.impl.TipoValidoRequest;
import com.example.rinhabackend.service.strategy.impl.ValorPositivoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/clientes/*")
public class ClienteServlet extends HttpServlet {

    public ClienteServlet() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ClienteRepository clienteRepository = new ClienteRepository();

    private final TransacaoRepository transacaoRepository = new TransacaoRepository();

    private final List<ValidacaoTransacaoRequest> validacoes = Arrays.asList(
            new ValorPositivoRequest(),
            new TipoValidoRequest(),
            new DescricaoCaracterMinMaxRequest());

    private final DebitoNaoPodeSerMenorLimite debitoNaoPodeSerMenorLimite = new DebitoNaoPodeSerMenorLimite();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) {
        try {
            List<Transacao> transacoes = transacaoRepository.findAll(obterIdCliente(req.getRequestURI()));
            response.getWriter().write(objectMapper.writeValueAsString(transacoes));
            response.setContentType("application/json");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

                registraTransacao(transacaoRequest, cliente);

                resp.setStatus(SC_OK);
            }
        } catch (ValidacaoRequestException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    private void registraTransacao(TransacaoRequest transacaoRequest, Cliente cliente) {
        Transacao transacao = new Transacao(
                transacaoRequest.getValor(),
                transacaoRequest.getTipo(),
                transacaoRequest.getDescricao(),
                LocalDateTime.now(),
                cliente.getId());

        transacaoRepository.save(transacao);
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