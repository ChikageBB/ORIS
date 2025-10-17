package ru.itis.dis403.lab04.service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import ru.itis.dis403.lab04.model.Airplane;
import ru.itis.dis403.lab04.repository.AirplaneRepository;

import java.util.List;

public class AirplaneService {
    private final AirplaneRepository repository = new AirplaneRepository();


    public void fillAttribute(HttpServletRequest request) {
        List<Airplane> airplanes = repository.findAll();
        request.setAttribute("airplanes", airplanes);
    }
}
