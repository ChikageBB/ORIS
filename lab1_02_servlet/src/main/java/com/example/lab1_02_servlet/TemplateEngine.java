package com.example.lab1_02_servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@WebServlet(value = "*.html")
public class TemplateEngine  extends HttpServlet {

    final static Logger logger = Logger.getLogger(TemplateEngine.class);
    private final TemplateHandler templateHandler = new TemplateHandler();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug(req.getServletPath());

        Map<String, String[]> params = new HashMap();
        req.getAttributeNames().asIterator().forEachRemaining(
                name -> {
                    Object value =  req.getAttribute(name);
                    if (value != null) {
                        params.put(name, new String[]{value.toString()});
                    }
                }
        );

        //url params
        params.putAll(req.getParameterMap());

        String fileName = req.getServletPath().substring(1);
        resp.setContentType("text/html;charset=UTF-8");

        try {
            templateHandler.handle(fileName, params, resp.getWriter());
        } catch (RuntimeException e) {
            System.out.printf("%s: %s\n", fileName, e.getMessage());
        }
    }
}
