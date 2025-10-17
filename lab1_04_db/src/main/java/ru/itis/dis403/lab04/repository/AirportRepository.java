package ru.itis.dis403.lab04.repository;

import ru.itis.dis403.lab04.service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportRepository {
    public List<String> findAll() {
        List<String> airports = new ArrayList<>();


        String sql = """
                    SELECT airport_code as code, airport_name ->> 'ru' as name FROM bookings.airports_data ORDER BY code;
                    """;

        try {
            Connection connection = DBConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String code = resultSet.getString("code");
                    String name = resultSet.getString("name");
                    airports.add(code + "-" + name);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airports;
    }
}
