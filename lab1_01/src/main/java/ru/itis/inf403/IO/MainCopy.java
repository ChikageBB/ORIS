package ru.itis.inf403.IO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainCopy {

    private String inputFile;
    private String outputFile;


    public static void main(String[] args) {
        MainCopy mc = new MainCopy();
        mc.input();
        mc.copy();

    }

    // Метод для ввода файлов
    private void input(){

        Scanner sc = new Scanner(System.in);

        System.out.println("Введите имя файла ");
        inputFile = sc.nextLine(); // читаем файл ввода

        System.out.println("Введите имя копии ");
        outputFile = sc.nextLine(); // читаем файл вывода

        File check = new File(inputFile); // проверяем существует ли файл
        if (!(check.isFile() && check.exists())){
            throw new RuntimeException("Файла не существует"); // если не существует выбрасываем исключения
        }

    }

    // Метод копирования файлв
    private void copy(){
        try(InputStream fis = new FileInputStream(inputFile);
            OutputStream fos = new FileOutputStream(outputFile)){ // создаем поток ввода и вывода

            byte[] buffer = new byte[1024]; // буффер на 1024 байт для чтения данных
            int r;

            // читаем файл блоками по 1024 байта
            while ((r = fis.read(buffer)) > -1){ // пока
                //fos.write(buffer, 0, r);
                // преобразуем прочитанные байты в строку в верхней регистре
                // преобразуем строку в байты
                fos.write(new String(buffer, 0, r).toUpperCase().getBytes(StandardCharsets.UTF_8));

            }
            // сбрасываем буфер ввода, чтобы убедится что данные записаны
            fos.flush();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


