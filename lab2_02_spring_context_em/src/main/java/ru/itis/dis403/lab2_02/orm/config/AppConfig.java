package ru.itis.dis403.lab2_02.orm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.itis.dis403.lab2_02.orm.orm.EntityManager;
import ru.itis.dis403.lab2_02.orm.orm.EntityManagerImpl;
import ru.itis.dis403.lab2_02.orm.orm.EntityScanner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Slf4j
@ComponentScan("ru.itis.dis403.lab2_02.orm")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/lab2");
        config.setUsername("postgres");
        config.setPassword("123");
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(50_000);
        log.info("DataSource (HikariCP) создан");
        return new HikariDataSource(config);
    }

    @Bean
    public EntityScanner entityScanner(DataSource dataSource) throws SQLException {
        EntityScanner scanner = new EntityScanner(
                dataSource,
                "ru.itis.dis403.lab2_02.orm.model"
        );

        scanner.createTables();
        scanner.validateSchema();
        return scanner;
    }


    @Bean
    @Scope("prototype")
    public EntityManager entityManager(DataSource dataSource, EntityScanner entityScanner) throws SQLException {
        Connection conn = dataSource.getConnection();
        return new EntityManagerImpl(conn);
    }
}
