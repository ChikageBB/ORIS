package ru.chikage.security.demo.lab2_07_docker.docker.subscriber;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.chikage.security.demo.lab2_07_docker.docker.model.MachineStatus;
import ru.chikage.security.demo.lab2_07_docker.docker.service.MachineStatusService;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class MachineStatusSubscriber {

    private final MachineStatusService machineStatusService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        new Thread(this::startListening).start();
    }

    private void startListening() {
        try (Connection connection = Nats.connect("nats://147.45.199.55:4222")) {
            Dispatcher dispatcher = connection.createDispatcher((msg) -> {
                try {
                    MachineStatus status = objectMapper.readValue(msg.getData(), MachineStatus.class);

                    machineStatusService.updateStatus(1, status);
                    System.out.println("Получен статус: " + status);
                } catch (Exception e) {

                }
            });

            dispatcher.subscribe("MachineStatus");
            System.out.println("NATS subscriber запущен...");

            Thread.currentThread().join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
