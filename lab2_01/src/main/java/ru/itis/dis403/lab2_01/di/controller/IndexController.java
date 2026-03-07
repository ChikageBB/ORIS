package ru.itis.dis403.lab2_01.di.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab2_01.di.annotation.Controller;
import ru.itis.dis403.lab2_01.di.annotation.GetMapping;

import java.io.IOException;

@Controller
public class IndexController {


    @GetMapping("/index")
    public void index(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;Charset=UTF-8");
        resp.getWriter().write("""
                    <html>
                                    <head><title>Index</title></head>
                                    <body>
                                        <h1>Индексная страница</h1>
                                        <p>Это /index</p>
                                        <a href="/home">На главную</a>
                                    </body>
                                    </html>
                """);
    }
}
