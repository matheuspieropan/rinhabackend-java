package com.example.rinhabackend.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseHttpUtil {

    private static final String CONTENT_TYPE = "application/json";

    public static void createdSucess(HttpServletResponse response) {
        configResponse(response, HttpServletResponse.SC_CREATED);
        try {
            response.getWriter().write("Salvo com sucesso");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void failed(HttpServletResponse response) {
        configResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            response.getWriter().write("Ocorreu um erro");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void configResponse(HttpServletResponse response, int statusResponse) {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(statusResponse);
    }
}