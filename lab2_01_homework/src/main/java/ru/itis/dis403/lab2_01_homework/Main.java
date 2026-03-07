package ru.itis.dis403.lab2_01_homework;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.itis.dis403.lab2_01_homework.config.AppConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws LifecycleException, IOException {

        Tomcat tomcat = new Tomcat();
        tomcat.getConnector().setPort(8080);

        String docBase = new File(".").getAbsolutePath();
        Context ctx = tomcat.addContext("", docBase);

        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);

        Wrapper wrapper = Tomcat.addServlet(ctx, "dispatcher", dispatcherServlet);
        wrapper.setLoadOnStartup(1);
        ctx.addServletMappingDecoded("/*", "dispatcher");

        tomcat.start();
        tomcat.getServer().await();
    }
}
