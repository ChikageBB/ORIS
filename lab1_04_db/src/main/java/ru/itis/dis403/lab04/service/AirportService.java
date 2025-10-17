package ru.itis.dis403.lab04.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.itis.dis403.lab04.repository.AirplaneRepository;
import ru.itis.dis403.lab04.repository.AirportRepository;

import java.util.List;

public class AirportService {
    private final AirportRepository airportRepository = new AirportRepository();
    public void fillAttribute(HttpServletRequest req) {
        List<String> airports = airportRepository.findAll();
        req.setAttribute("airports", airports);
    }
}
