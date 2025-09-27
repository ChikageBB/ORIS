package com.example.lab1_02_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet("/template/test")
public class TemplateTest extends HttpServlet {

    final static Logger logger = Logger.getLogger(TemplateTest.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug(req.getServletPath());


        req.setAttribute("default", "Ваше имя");
        req.setAttribute("param2", "Ваше имя");

        // Обрабатываем логику приложения
        // Отрисовка страницы - передаем дальше request

        req.getRequestDispatcher("/test.html").forward(req, resp);
    }
}
