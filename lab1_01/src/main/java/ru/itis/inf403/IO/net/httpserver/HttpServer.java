package ru.itis.inf403.IO.net.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class HttpServer {

    final static Logger logger = LogManager.getLogger(HttpServer.class);

    public static void main(String[] args) {


        new Application().init();

        RequestHandler requestHandler = new RequestHandler();

        try {
            ServerSocket server = new ServerSocket(8080);

            while (true) {
                 // Ожидаем подключение клиента
                Socket client = server.accept();
                requestHandler.handle(client);

                new Thread( () -> requestHandler.handle(client)).start();

            }
        } catch (IOException e) {
            logger.atError().withThrowable(e);
            e.printStackTrace();
        }

    }
}