package ru.chikage.security.demo.lab2_07_docker.nats;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Subscription;

import java.io.IOException;
import java.time.Duration;

public class BasicPubSub {
    public static void main(String[] args) {
        String subject = "11-403.messages";

        try (Connection connection = Nats.connect("nats://147.45.199.55:4222")) {
            Subscription sub = connection.subscribe(subject);

            // Отправка сообщения
            connection.publish(subject, "Сегодня тема NATS".getBytes());
            System.out.println("Сообщение опубликовано на тему: " + subject);

            // Ожидание сообщения (с таймаутом в 1 секунду)
            Message msg = sub.nextMessage(Duration.ofSeconds(3));
            if (msg != null) {
                String response = new String(msg.getData());
                System.out.println("Получено сообщение: " + response);
            } else {
                System.out.println("Сообщение не получено.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
