package ru.chikage.security.demo.lab2_07_docker.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class TestMachineClient {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(2))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8090/api/status/1"))
                .build();


        CompletableFuture<HttpResponse<String>> response =  client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(r -> System.out.println(r.body()))
                .exceptionally(e -> {
                    System.out.println(e.getMessage());
                    return null;
                });

        Thread.sleep(300);
    }
}
