package ru.itis.inf403.IO.net.DZ;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {


    public static void main(String[] args) {


       try (    // создаем серверный сокет, прослушивающий порт 50000
                ServerSocket server = new ServerSocket(50_000);
                // Ожидаем подключение клиента
                Socket client = server.accept()){


           System.out.println("Сервер запущен");

           // поток ввода для чтения сообщений клиента
           BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

           // выходной поток для отправки сообщений клиенту
           BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

           Scanner scanner = new Scanner(System.in);

           while (true){
               String clientMessage = in.readLine(); // читаем сообщение клиента
               System.out.println("Сообщение от клиента: " + clientMessage);

               // если клиент отправит "exit", выходим из цикла
               if (clientMessage.equals("exit")){
                   break;
               }

               // Пишем сообщение от сервера
               String serverMessage = scanner.nextLine();

               // записываем сообщение от сервера
               out.write(serverMessage);
               out.newLine(); // записываем новую строку
               out.flush(); // сбрасываем все из буфера, чтобы убедиться что все отправлено

               if (serverMessage.equals("exit")){
                   break;
               }

           }

       }catch (IOException e){
            e.printStackTrace();
       }

    }
}
