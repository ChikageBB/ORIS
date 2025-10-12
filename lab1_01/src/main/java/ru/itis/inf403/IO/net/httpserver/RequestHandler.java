package ru.itis.inf403.IO.net.httpserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    private static final Logger logger = LogManager.getLogger(HttpServer.class);
    public void handle(Socket client)  {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            //http://localhost:8080/resource/part?name=tat&region=16
            // GET /resource/part?name=tat&region=16 HTTP1.1
            String lineOne = in.readLine(); // читаем первую сообщение запроса - GET /resource/part?name=tat&region=16 HTTP1.1
            System.out.println(lineOne);
            logger.debug(lineOne);
            String[] components = lineOne.split(" "); // разбиваем по частям
            String method = components[0]; // GET / POST
            String path = components[1]; // /home/part?name=tat&region=16

            int idx = path.indexOf('?'); // определяем индекс ? откуда будут идти параметры URL

            String resource = path;
            String query = null;


            if (idx != -1) { // если индекс не -1 значит есть параметры
                resource = path.substring(0, idx);
                query = path.substring(idx + 1);
            }

            Map<String, String> params = new HashMap<>();
            // name=tat
            //[name, tat] [region, 16]

            if (query != null) {
                for (String param: query.split("&")) { // разбиваем пары параметров по &
                    String[] pair = param.split("=");
                    if (pair.length > 1) {
                        params.put(pair[0], pair[1]);
                    } else {
                        params.put(pair[0], "");
                    }
                }
            }

            if (resource.contains("/shutdown")) {
                logger.info("server stopped");
            }

//            if (path.equals("/shutdown")) {
//                logger.info("server stopped");
//                //  break;
//            }

            while (true){
                String clientMessage = in.readLine();
                System.out.println(clientMessage);

                logger.debug(clientMessage);

                if (clientMessage.isEmpty()) {
                    logger.debug("end of request header");
                    OutputStream os = client.getOutputStream();
                    logger.debug("outputStream" + os);

                    IResourceService resourceService = Application.resourceMap.get(resource);

                    if (resourceService != null) {
                        resourceService.service("GET", params, os);
                    } else {
                        new NotFoundHomeService().service("GET", params, os);
                    }

                    os.flush();
                    logger.debug(os);
                    break;
                }
            }
        } catch (IOException e) {

        }

    }
}
