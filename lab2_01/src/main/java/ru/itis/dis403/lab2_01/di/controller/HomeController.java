package ru.itis.dis403.lab2_01.di.controller;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab2_01.di.annotation.Controller;
import ru.itis.dis403.lab2_01.di.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/home")
    public void home(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("<h1>Home page</h1>");
    }
}
