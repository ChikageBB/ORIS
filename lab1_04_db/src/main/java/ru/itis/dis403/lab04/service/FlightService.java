package ru.itis.dis403.lab04.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.itis.dis403.lab04.model.Flight;
import ru.itis.dis403.lab04.repository.FlightRepository;

import java.util.List;

public class FlightService {

    private final FlightRepository flightRepository = new FlightRepository();

    public void fillAttribute(HttpServletRequest req) {

        String airport = req.getParameter("airport");
        String type = req.getParameter("type");

        if (airport == null || type == null) {
            req.setAttribute("flights", List.of());
            return;
        }

        List<Flight> flights = flightRepository.findAll(airport, type);
        req.setAttribute("flights", flights);
    }

}
