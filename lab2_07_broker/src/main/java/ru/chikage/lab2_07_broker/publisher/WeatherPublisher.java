package ru.chikage.lab2_07_broker.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.nats.client.Connection;
import io.nats.client.Nats;
import ru.chikage.lab2_07_broker.model.Weather;


import java.util.Random;

public class WeatherPublisher {
    public static void main(String[] args) {
        String subject = "Weather";

        try (Connection connection = Nats.connect("nats://147.45.199.55:4222")) {
            Random random = new Random();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


            while (true) {
                Weather weather = Weather.builder()
                        .city("Казань")
                        .temp(10. + random.nextDouble() * 2 - 1)
                        .pressure(744 + random.nextDouble() * 4 - 2)
                        .windSpeed(3 + random.nextDouble() * 4 - 2)
                        .windDirection("СЗ")
                        .build();

                byte[] msg = objectMapper.writeValueAsBytes(weather);
                connection.publish(subject, msg);
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
