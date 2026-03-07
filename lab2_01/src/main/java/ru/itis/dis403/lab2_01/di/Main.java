package ru.itis.dis403.lab2_01.di;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import ru.itis.dis403.lab2_01.di.component.Application;
import ru.itis.dis403.lab2_01.di.config.Context;
import ru.itis.dis403.lab2_01.di.config.PathScan;
import ru.itis.dis403.lab2_01.di.web.DispatcherServlet;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        Connector connector = new Connector();
        connector.setPort(8090);
        tomcat.setConnector(connector);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        var context = tomcat.addContext(contextPath, docBase);

        String servletName = "dispatcherServlet";
        tomcat.addServlet(contextPath, servletName, new DispatcherServlet());
        context.addServletMappingDecoded("/*", servletName);

        try {
            tomcat.start();
            tomcat.getServer().await();

        } catch (LifecycleException e) {

        }
     }
}
