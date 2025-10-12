package ru.itis.dis403.lab04;

import java.sql.*;

public class TestDB {
    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");

            Connection connection =
                    DriverManager.getConnection(
                            // адрес БД , имя пользователя, пароль
                            "jdbc:postgresql://localhost:5432/demo", "postgres", "123");

            //Boolean result = statement.execute("create table users(id bigint primary key, name varchar(50))");

            String sql = "select * from bookings.airplanes_data where airplane_code = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, "35X");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.print(resultSet.getString("airplane_code") + ";");
                System.out.print(resultSet.getString("model"));
                System.out.print(resultSet.getString("range"));
                System.out.println(resultSet.getString("speed"));
            }

            resultSet.close();

            statement.close();
            connection.close();


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}