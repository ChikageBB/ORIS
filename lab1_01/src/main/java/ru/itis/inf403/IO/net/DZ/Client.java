package ru.itis.inf403.IO.net.DZ;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {


       try{
           // Подключаемся к серверу по адресу localhost и порту 50000
           Socket client = new Socket("localhost", 50000);

           // входной поток для чтения ответа от сервера
           BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
           // выходной поток для отправки ответа
           BufferedWriter os = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

           while (true){

               Scanner sc = new Scanner(System.in);
               String message = sc.nextLine(); // пишем сообщение

               // отправляем сообщение на сервер
               os.write(message);
               os.newLine();
               os.flush();

               // если сообщение "exit" выходим из цикла
               if (message.equals("exit")){
                   break;
               }


               String serverMessage = is.readLine(); // читаем сообщение от сервера

               System.out.println("Ответ сервера: " + serverMessage); // выводим сообщение от сервера

               // если сообщение сервера "exit" выходим из цикла
               if (serverMessage.equals("exit")){
                   break;
               }
           }

           is.close();
           os.close();
           client.close();

       }catch (IOException e){
           e.printStackTrace();
       }

    }
}
