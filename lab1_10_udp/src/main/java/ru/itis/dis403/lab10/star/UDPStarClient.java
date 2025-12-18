package ru.itis.dis403.lab10.star;

// UDPClient.java
import lombok.Data;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class UDPStarClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 50000;
    private static final int TIMEOUT = 5000; // 5 секунд
    private static final int BUFFER_SIZE = 4096;
    private static volatile boolean running = true;

    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket()) {
            //socket.setSoTimeout(TIMEOUT);

            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            Scanner scanner = new Scanner(System.in);

            System.out.println("UDP клиент запущен");
            System.out.println("Подключение к серверу: " + SERVER_ADDRESS + ":" + SERVER_PORT);
            System.out.println("Введите 'exit' для выхода");
            System.out.println("Доступные команды: hello, list, message");
            System.out.println("------------------------------------------");

            // параллельный процесс прослушивания сервера
            Thread receiveThread = new Thread(() -> {
                byte[] receiveData = new byte[BUFFER_SIZE];
                while (running) {
                    try {
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        socket.receive(receivePacket);

                        byte[] packetData = new byte[receivePacket.getLength()];
                        System.arraycopy(receivePacket.getData(), 0, packetData, 0, receivePacket.getLength());


                        DataInputStream dis = new DataInputStream(
                                new ByteArrayInputStream(packetData)
                        );

                        if (packetData.length > 0) {
                            byte typeMsg = dis.readByte();

                            switch (typeMsg) {
                                case 1 -> {
                                    int size = dis.readInt();
                                    byte[] msg = new byte[size];
                                    dis.read(msg);
                                    String list = new String(msg, StandardCharsets.UTF_8);
                                    System.out.println(list);
                                    System.out.print("\n> ");
                                }
                                case 2 -> {
                                    int senderId = dis.readInt();
                                    int size = dis.readInt();
                                    byte[] msg = new byte[size];
                                    dis.read(msg);
                                    String message = new String(msg, StandardCharsets.UTF_8);

                                    System.out.println("\n> От клиента: " + senderId);
                                    System.out.println("Текст: " + message);
                                    System.out.println("> ");
                                }
                            }
                        }

                    } catch (SocketTimeoutException e) {
                        System.out.println("Превышено время ожидания ответа от сервера");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            receiveThread.setDaemon(true);
            receiveThread.start();

            // опрос ввода с консоли
            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine().trim();

                if (command.isEmpty()) {
                    continue;
                }

                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Завершение работы клиента...");
                    break;
                }
                try {
                    switch (command.toLowerCase()) {
                        case "hello" ->  {
                            System.out.println("введите имя:");
                            String name = scanner.nextLine();

                            byte[] data = name.getBytes(StandardCharsets.UTF_8);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bos.write(0);
                            bos.write(writeInt(data.length));
                            bos.write(data);

                            DatagramPacket sendPacket = new DatagramPacket(
                                    bos.toByteArray(),
                                    bos.size(),
                                    serverAddress,
                                    SERVER_PORT
                            );
                            socket.send(sendPacket);
                            System.out.println("✓ Регистрация отправлена");
                        }
                        case "list" -> {
                            DatagramPacket sendPacket = new DatagramPacket(
                                    new byte[]{1},
                                    1,
                                    serverAddress,
                                    SERVER_PORT
                            );

                            socket.send(sendPacket);

                            System.out.println("✓ Запрос списка отправлен");
                        }
                        case "message" -> {
                            System.out.println("введите id получателя:");
                            String id = scanner.nextLine();
                            int recipientId = Integer.parseInt(id);


                            System.out.println("введите сообщение получателю:" + id);
                            String msg = scanner.nextLine();

                            byte[] data = msg.getBytes(StandardCharsets.UTF_8);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bos.write(2);
                            bos.write(writeInt(recipientId));
                            bos.write(writeInt(data.length));
                            bos.write(data);

                            DatagramPacket sendPacket = new DatagramPacket(
                                    bos.toByteArray(),
                                    bos.size(),
                                    serverAddress,
                                    SERVER_PORT
                            );
                            socket.send(sendPacket);
                            System.out.println("✓ Сообщение отправлено клиенту #" + recipientId);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Ошибка: введите корректный числовой ID");
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        }
    }

    private static byte[] writeInt(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value >> 24);
        result[1] = (byte) (value >> 16);
        result[2] = (byte) (value >> 8);
        result[3] = (byte) value;
        return result;
    }
}