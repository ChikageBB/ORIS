package ru.chikage.security.demo.lab2_07_docker.nats;

import io.nats.client.Connection;
import io.nats.client.Nats;

public class JetStreamExample {
    public static void main(String[] args) {

        try (Connection connection = Nats.connect("nats://localhost:4222")) {



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
