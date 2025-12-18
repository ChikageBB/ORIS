package ru.itis.dis403.lab05.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.dis403.lab05.util.FreemarkerUtil;

import java.io.IOException;
import java.util.Map;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
        Map<String, Object> model = FreemarkerUtil.createModel(req);
        FreemarkerUtil.render("login.ftl", model, resp);

    }
}
