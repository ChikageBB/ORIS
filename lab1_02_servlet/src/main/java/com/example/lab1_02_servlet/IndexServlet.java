package com.example.lab1_02_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.transform.sax.TemplatesHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
        Map<String, String> params = new HashMap<>();

        params.put("username", "санек пидор");

        new TemplateHandler().handle("index.html", params,  servletResponse.getWriter());
    }

    public void doPost(HttpServletRequest servletRequest,
                       HttpServletResponse servletResponse) throws ServletException, IOException {
        doGet(servletRequest, servletResponse);
    }

}
