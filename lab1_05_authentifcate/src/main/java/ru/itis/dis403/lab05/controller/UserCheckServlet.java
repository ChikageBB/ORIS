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


@WebServlet("/usercheck")
public class UserCheckServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Map<String, Object> model = FreemarkerUtil.createModel(req);

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "admin".equals(password)) {
            req.getSession(true).setAttribute("user", username);
            resp.sendRedirect(req.getContextPath() + "/index");
        } else {
            model.put("errormessage", "Неверное имя пользователя или пароль!");
            FreemarkerUtil.render("login.ftl", model, resp); // показываем login
        }
    }
}
