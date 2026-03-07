package ru.itis.dis403.lab2_02.orm;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.dis403.lab2_02.orm.config.AppConfig;
import ru.itis.dis403.lab2_02.orm.model.City;
import ru.itis.dis403.lab2_02.orm.model.Country;
import ru.itis.dis403.lab2_02.orm.model.Street;
import ru.itis.dis403.lab2_02.orm.orm.EntityManager;
import ru.itis.dis403.lab2_02.orm.orm.EntityManagerImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        EntityManager em = context.getBean(EntityManager.class);

        Country ru = new Country();
        ru.setName("Russia");
        em.save(ru);
        System.out.println("Saved country id=" + ru.getId());

        City kazan = new City();
        kazan.setName("Kazan");
        kazan.setCountry(ru);
        em.save(kazan);
        System.out.println("Saved city id=" + kazan.getId());

        Street street = new Street();
        street.setName("Kremlyovskaya");
        street.setCity(kazan);
        em.save(street);
        System.out.println("Saved street id=" + street.getId());


        Street found = em.find(Street.class, street.getId());

        System.out.println("Found: " + found);
        System.out.println("City: " + found.getCity().getName());
        System.out.println("Country: " + found.getCity().getCountry().getName());

        // ── findAll ──────────────────────────────────
        System.out.println("All streets: " + em.findAll(Street.class));

        // ── Update ────────────────────────────────────
        found.setName("Kremlyovskaya St.");
        em.save(found);

        // ── Remove ────────────────────────────────────
        em.remove(found);
        System.out.println("After remove: " + em.find(Street.class, found.getId()));

        em.close();
        context.close();
    }
}
