package ru.itis.dis403.lab04.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.dis403.lab04.service.DBConnection;

import java.sql.SQLException;


@WebListener
public class DBContextListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.postgresql.Driver");

            DBConnection.getConnection();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.releaseConnection();
    }
}
