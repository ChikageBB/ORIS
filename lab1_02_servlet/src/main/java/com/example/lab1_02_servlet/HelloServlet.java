package com.example.lab1_02_servlet;

import java.io.*;
import java.util.Map;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebServlet("/hello")
public class HelloServlet implements Servlet {
    private final static Logger logger = LogManager.getLogger(HelloServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        logger.debug(servletConfig.getServletName());
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        logger.debug(servletRequest);
        String protocol = servletRequest.getProtocol();
        String remoteAddr = servletRequest.getRemoteAddr();
        int remotePort = servletRequest.getRemotePort();
        Map<String, String[]> parameters = servletRequest.getParameterMap();


        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>")
                .append("<div>protocol: ").append(protocol).append("</div>")
                .append("<div>remoteAddr: ").append(remoteAddr).append("</div>")
                .append("<div>remotePort: ").append(remotePort).append("</div>");

        parameters.entrySet().stream().forEach(e -> {
            sb.append("<div>param: ").append(e.getKey()).append("</div>");
        });
        sb.append("</body></html>");
        Writer writer = servletResponse.getWriter();
        writer.write(sb.toString());
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}