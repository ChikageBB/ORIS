package ru.itis.dis403.lab2_01.et;


import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class TestEmbeddedTomcat {
    public static void main(String[] args) {

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        Connector connector = new Connector();
        connector.setPort(8090);
        tomcat.setConnector(connector);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        var context = tomcat.addContext(contextPath, docBase);

        HttpServlet servlet = new HttpServlet() {
            @Override
            public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.setContentType("text/html; charset=utf-8");
                PrintWriter writer = resp.getWriter();
                writer.println("<html><head><meta charset='utf-8'/><title>Embeded Tomcat</title></head><body>");
                writer.println("<h1>Мы встроили Tomcat в свое приложение!</h1>");

                writer.println("<div>Метод: " + req.getMethod() + "</div>");
                writer.println("<div>Ресурс: " + req.getPathInfo() + "</div>");
                writer.println("</body></html>");
            }
        };

        String servletName = "dispatcherServlet";
        tomcat.addServlet(contextPath, servletName, servlet);
        context.addServletMappingDecoded("/*", servletName);
        try {
            tomcat.start();
            tomcat.getServer().await();

            /*
                tomcat.stop()
                tomcat.destroy()
             */
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
