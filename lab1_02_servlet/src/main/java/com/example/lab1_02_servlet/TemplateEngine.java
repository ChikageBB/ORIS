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

@WebServlet(value = "*.html")
public class TemplateEngine  extends HttpServlet {

    final static Logger logger = Logger.getLogger(TemplateEngine.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug(req.getServletPath());


        String fileName = req.getServletPath().substring(1);
        URL url = TemplateEngine.class.getClassLoader().getResource("templates/" + fileName);

        String template = null;
        try {
            template = Files.readString(Paths.get(url.toURI()));
            resp.getWriter().write(template);
        } catch (URISyntaxException e) {
            logger.error("Error reading template file: " + e.getMessage());
            resp.sendError(500, "Internal server error");
        }


    }
}
