package ru.itis.dis403.lab2_01.di.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab2_01.di.config.Context;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {

    private Context context;

    @Override
    public void init() throws ServletException {
        context = new Context();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        Method method = context.getHandler(path);

        if (method == null) {
            resp.setStatus(404);
            resp.getWriter().write("404 NOT FOUND");
            return;
        }

        try {
            Object controller = context.getMethodOwner(method);

            method.invoke(controller, req, resp);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
