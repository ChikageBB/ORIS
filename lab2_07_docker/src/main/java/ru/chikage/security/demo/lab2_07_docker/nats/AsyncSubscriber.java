package ru.chikage.security.demo.lab2_07_docker.nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;

import java.io.IOException;

public class AsyncSubscriber {
    public static void main(String[] args) {

        String subject = "11-403.messages";

        try (Connection connection = Nats.connect("nats://147.45.199.55:4222")) {

            Dispatcher dispatcher = connection.createDispatcher((msg) -> {
                System.out.printf("Тема: %s, Сообщение: %s%n",
                        msg.getSubject(), new String(msg.getData()));
            });

            dispatcher.subscribe(subject);
            System.out.println("Ожидание сообщений на тему: " + subject);

            Thread.sleep(60000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
