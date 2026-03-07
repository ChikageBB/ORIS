package ru.itis.dis403.spring.jpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.dis403.spring.jpa.config.Config;

public class Application {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(Config.class);


    }
}
