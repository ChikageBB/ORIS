package ru.itis.dis403.lab2_01_homework.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class IndexController {

    @GetMapping("/index")
    public void index(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
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
