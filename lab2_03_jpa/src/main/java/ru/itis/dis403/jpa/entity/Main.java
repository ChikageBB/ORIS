package ru.itis.dis403.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory =
                new Configuration()
                        .configure() // автоматически ищет hibernate.cfg.xml
                        .buildSessionFactory();

        // открываем сессию
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Client client = new Client();
        client.setName("Roman");
        client.setAddress("Moscow");

        session.persist(client);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}
