package com.example.lab1_02_servlet;


import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class TemplateHandler {
    public void handle(String templateName, Map<String, String> map, Writer writer) {

        /*
        1. Найти файл по имени templateName
        2. Прочитать файл в строку
        3. Найти в файле ${param_name} и заменить на значения параметра
        4. Передать строку во writer
        */
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("templates/" + templateName);
            String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String param = "${" + entry.getKey() + "}";
                template = template.replace(param, entry.getValue());
            }

            writer.write(template);

        } catch (IOException e) {
            System.out.println("Шаблон не найден");
            throw new RuntimeException(e);
        }
    }
}
