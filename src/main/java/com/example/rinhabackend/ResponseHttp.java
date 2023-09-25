package com.example.rinhabackend;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseHttp {

    private static final String CONTENT_TYPE = "application/json";

    public static void createdSucess(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            response.getWriter().write("Salvo com sucesso");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void failed(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            response.getWriter().write("Ocorreu um erro");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}