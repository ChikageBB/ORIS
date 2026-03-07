package ru.itis.dis403.lab2_01_homework.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/home")
    public void home(HttpServletResponse resp) throws IOException {
        resp.getWriter().write("<h1>Home page</h1>");
    }
}
