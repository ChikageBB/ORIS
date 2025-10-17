package ru.itis.dis403.lab04;

import java.sql.*;

public class TestDb {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/demo",
                    "postgres",
                    "123"
            );

            Statement statement = connection.createStatement();
            String sql = """
                    SELECT * FROM bookings.airplanes_data;
                    """;

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                System.out.print(resultSet.getString("airplane_code") + ";");
                System.out.print(resultSet.getString("model") + ";");
                System.out.print(resultSet.getString("range") + ";");
                System.out.println(resultSet.getString("speed"));
            }

            resultSet.close();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
