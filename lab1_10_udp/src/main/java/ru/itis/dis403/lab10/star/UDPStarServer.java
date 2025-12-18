package ru.itis.dis403.lab10.star;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Cleanup;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UDPStarServer {
    private static final int PORT = 50000;
    private static final int BUFFER_SIZE = 4096;
    private DatagramSocket socket;
    private boolean running;
    private byte[] buffer = new byte[BUFFER_SIZE];
    private volatile int nextClientID = 1;

    private List<ClientData> clients;

    public UDPStarServer() {
        clients = new ArrayList<>();
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("UDP сервер запущен на порту " + PORT);
            running = true;
        } catch (SocketException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
            System.exit(1);
        }
    }

    public void start() {
        System.out.println("Сервер работает... Нажмите Ctrl+C для остановки");

        while (running) {
            try {
                // Создаем пакет для получения данных от клиента
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

                // Ожидаем данные
                socket.receive(receivePacket);

                // Получаем информацию о клиенте
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Обрабатываем сообщение
                processMessage(receivePacket);


            } catch (IOException e) {
                System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            }
        }

        socket.close();
        System.out.println("Сервер остановлен");
    }

    private void processMessage(DatagramPacket receivePacket) throws IOException {

        byte[] data = receivePacket.getData();
        @Cleanup DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        byte msgType = dis.readByte();

        switch (msgType) {
            case 0 -> {
                int length = dis.readInt();
                byte[] buf = new byte[length];
                dis.read(buf, 0, length);
                String name = new String(buf, StandardCharsets.UTF_8);
                clients.add(new ClientData(nextClientID++, name,
                        receivePacket.getAddress(), receivePacket.getPort()));
                System.out.println("Добавили клиента " + name);
            } case 1 -> {
                ObjectMapper mapper = new ObjectMapper();
                String message = mapper.writeValueAsString(clients);
                byte[] dataMessage = message.getBytes(StandardCharsets.UTF_8);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bos.write((byte)1);
                bos.write(writeInt(dataMessage.length));
                bos.write(dataMessage);

                DatagramPacket sendPacket = new DatagramPacket(
                        bos.toByteArray(),
                        bos.size(),
                        receivePacket.getAddress(),
                        receivePacket.getPort());
                socket.send(sendPacket);
                System.out.println("Список отправлен");
            } case 2 -> {
                int id = dis.readInt();
                int size = dis.readInt();
                byte[] msg = new byte[size];
                dis.read(msg);


                Optional<ClientData> sender = clients.stream()
                        .filter(c -> c.getInetAddress().equals(receivePacket.getAddress())
                                && c.getClientPort() == receivePacket.getPort())
                        .findFirst();

                Optional<ClientData> recipient = clients.stream()
                        .filter(c -> c.getId() == id)
                        .findFirst();

                if (recipient.isPresent() && sender.isPresent()) {
                    ClientData recipientClient = recipient.get();
                    ClientData senderClient = sender.get();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bos.write((byte) 2);
                    bos.write(writeInt(senderClient.getId()));
                    bos.write(writeInt(msg.length));
                    bos.write(msg);

                    DatagramPacket sendPacket = new DatagramPacket(
                            bos.toByteArray(),
                            bos.size(),
                            recipientClient.getInetAddress(),
                            recipientClient.getClientPort()
                    );

                    socket.send(sendPacket);
                } else if (!recipient.isPresent()) {
                    String errorMsg = "Клиент с ID" + id + " не найден";
                    byte[] errorData = errorMsg.getBytes(StandardCharsets.UTF_8);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bos.write((byte) 3);
                    bos.write(writeInt(errorData.length));


                    DatagramPacket errorPacket = new DatagramPacket(
                            bos.toByteArray(),
                            bos.size(),
                            receivePacket.getAddress(),
                            receivePacket.getPort()
                    );

                    socket.send(errorPacket);
                }

            }
        }
    }

    public static byte[] writeInt(int i) {
        return new byte[]{
                (byte) ((i >>> 24) & 0xFF),
                (byte) ((i >>> 16) & 0xFF),
                (byte) ((i >>> 8) & 0xFF),
                (byte) (i & 0xFF)
        };
    }

    public static void main(String[] args) {
        UDPStarServer server = new UDPStarServer();
        server.start();
    }
}