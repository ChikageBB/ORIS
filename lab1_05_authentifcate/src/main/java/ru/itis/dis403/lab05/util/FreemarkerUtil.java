package ru.itis.dis403.lab05.util;

import freemarker.template.*;
import jakarta.servlet.http.HttpSession;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerUtil {
    private static Configuration configuration;

    public static void init() {
        configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setClassForTemplateLoading (FreemarkerUtil.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void render(String templateName, Map<String, Object> data,
                              HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (Writer wr = response.getWriter()) {
            Template template = configuration.getTemplate(templateName);
            template.process(data, wr);

        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }


    public static Map<String, Object> createModel(HttpServletRequest req) {
        Map<String, Object> model = new HashMap<>();
        model.put("contextPath", req.getContextPath());
        model.put("req", req);


        return model;

    }
}
