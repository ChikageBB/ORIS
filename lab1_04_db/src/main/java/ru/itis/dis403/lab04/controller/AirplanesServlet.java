package ru.itis.dis403.lab04.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab04.service.AirplaneService;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/airplanes")
public class AirplanesServlet extends HttpServlet {

    private final AirplaneService airplaneService = new AirplaneService();

    private Configuration cfg;

    @Override
    public void init() throws ServletException {
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(AirplanesServlet.class, "/template");
        cfg.setDefaultEncoding("UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        airplaneService.fillAttribute(req);


        Map<String, Object> model = new HashMap<>();
        model.put("airplanes", req.getAttribute("airplanes"));


        Template template = cfg.getTemplate("airplanes.ftlh");
        try (Writer out = resp.getWriter()) {
            template.process(model, out);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
