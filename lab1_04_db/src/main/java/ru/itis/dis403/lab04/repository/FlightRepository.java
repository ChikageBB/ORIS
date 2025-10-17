package ru.itis.dis403.lab04.repository;

import ru.itis.dis403.lab04.model.Flight;
import ru.itis.dis403.lab04.service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
ToDo:
    Дома: онлайн табло, выбирается аэропорт (из списка), день, время берете текущее, тип табло (прилет, вылет)
    выводите номер рейса, время отправления, время прилета, состояние , пункт вылета/назначения (в зависимости от типа табло)
    Использовать таблицы рейсы, маршруты, аэропорты
 */
public class FlightRepository {

    public List<Flight> findAll(String airportCode, String type) {
        List<Flight> flights = new ArrayList<>();

        String sql;

        if ("departure".equalsIgnoreCase(type)) {
            sql = """
                    
                    SELECT f.flight_id,
                           f.scheduled_departure,
                           f.scheduled_arrival,
                           f.status,
                           a1.airport_name ->> 'ru' as departure_name,
                           a2.airport_name ->> 'ru' as arrival_name
                    
                    FROM flights f
                            JOIN bookings.routes r ON f.route_no = r.route_no
                            JOIN bookings.airports_data a1 ON r.departure_airport = a1.airport_code
                            JOIN bookings.airports_data a2 ON r.arrival_airport = a2.airport_code
                    WHERE r.departure_airport = ? AND f.scheduled_departure >= NOW()
                    ORDER BY f.scheduled_departure
                    LIMIT 30;
                    """;
        } else {
            sql = """
                    SELECT f.flight_id,
                           f.scheduled_departure,
                           f.scheduled_arrival,
                           f.status,
                           a1.airport_name ->> 'ru' as departure_name,
                           a2.airport_name ->> 'ru' as arrival_name
                    
                    FROM flights f
                            JOIN bookings.routes r ON f.route_no = r.route_no
                            JOIN bookings.airports_data a1 ON r.departure_airport = a1.airport_code
                            JOIN bookings.airports_data a2 ON r.arrival_airport = a2.airport_code
                    WHERE r.arrival_airport = ? AND f.scheduled_arrival >= NOW()
                    ORDER BY f.scheduled_arrival
                    LIMIT 30;
                    """;
        }

        try {
            Connection connection = DBConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, airportCode);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    flights.add(new Flight(
                            resultSet.getString("flight_id"),
                            new Date(resultSet.getTimestamp("scheduled_departure").getTime()),
                            new Date(resultSet.getTimestamp("scheduled_arrival").getTime()),
                            resultSet.getString("status"),
                            resultSet.getString("departure_name"),
                            resultSet.getString("arrival_name")
                    ));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
