package ru.itis.dis403.lab05.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab05.util.FreemarkerUtil;

import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = FreemarkerUtil.createModel(req);
        model.put("errormessage", req.getAttribute("errormessage")); // если нужно

        FreemarkerUtil.render("login.ftl", model, resp);
    }
}
