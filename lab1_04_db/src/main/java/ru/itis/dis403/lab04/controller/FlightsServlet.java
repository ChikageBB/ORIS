package ru.itis.dis403.lab04.controller;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab04.service.AirportService;
import ru.itis.dis403.lab04.service.FlightService;
import freemarker.template.Configuration;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/flights")
public class FlightsServlet extends HttpServlet {


    private final FlightService flightService = new FlightService();
    private final AirportService airportService = new AirportService();

    private Configuration cfg;

    @Override
    public void init() throws ServletException {
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(FlightsServlet.class, "/template");
        cfg.setDefaultEncoding("UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        flightService.fillAttribute(req);
        airportService.fillAttribute(req);

        Map<String, Object> model = new HashMap<>();
        model.put("flights", req.getAttribute("flights"));
        model.put("airports", req.getAttribute("airports"));
        model.put("request", req);


        Template template = cfg.getTemplate("flights.ftlh");

        try (Writer out = resp.getWriter()) {
            template.process(model, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
