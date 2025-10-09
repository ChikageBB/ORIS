package ru.itis.dis403.lab03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/test")
public class FreemarkerTest extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Устанавливаем кодировку для запроса
        request.setCharacterEncoding("UTF-8");
        // Устанавливаем кодировку для ответа
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("surname", "Еникеев");
        request.setAttribute("name", "Камиль");

        // Отрабатываем логику приложения
        // Отрисовка страницы - передаем дальше request
        request.getRequestDispatcher("/index.ftlh")
                .forward(request, response);
    }
}