package ru.itis.dis403.lab05;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.dis403.lab05.util.FreemarkerUtil;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FreemarkerUtil.init();
        
    }
}
