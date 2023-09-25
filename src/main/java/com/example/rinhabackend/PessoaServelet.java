package com.example.rinhabackend;

import com.example.rinhabackend.persistence.PersistenceFactory;
import com.example.rinhabackend.entity.Pessoa;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet
public class PessoaServelet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write("teste");
        response.getWriter().flush();
        String query = "select * from pessoa";
        List<Pessoa> pessoas = PersistenceFactory.getInstance().createNativeQuery(query).getResultList();
        System.out.println(pessoas);
    }
}