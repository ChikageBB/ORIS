package ru.itis.dis403.lab04.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab04.model.Airplane;
import ru.itis.dis403.lab04.repository.AirplaneRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneService {

    private AirplaneRepository airplaneRepository = new AirplaneRepository();

    public void fillAttributes(HttpServletRequest req){
        List<Airplane> airplanes = airplaneRepository.findAll();

        req.setAttribute("airplanes", airplanes);
    }
}
