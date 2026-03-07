package ru.itis.dis403.lab2_02.orm.orm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itis.dis403.lab2_02.orm.annotation.Column;
import ru.itis.dis403.lab2_02.orm.annotation.Entity;
import ru.itis.dis403.lab2_02.orm.annotation.Id;
import ru.itis.dis403.lab2_02.orm.annotation.ManyToOne;

import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class EntityScanner {


    private final DataSource dataSource;
    private final String basePackage;

    private Set<Class<?>> entities;

    public Set<Class<?>> scanEntities() {
        if (entities != null) return entities;

        entities = new HashSet<>();

        String packagePath = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            URL resource = classLoader.getResource(packagePath);
            if (resource == null) {
                log.error("Пакет не найден: {}", packagePath);
                return entities;
            }

            File packageDir = new File(resource.toURI());

            if (!packageDir.exists() || !packageDir.isDirectory()) {
                log.error("Директория пакета не существует: {}", packageDir);
                return entities;
            }

            File[] classFiles = packageDir.listFiles(
                    file -> file.isFile() && file.getName().endsWith(".class")
            );

            if (classFiles == null) {
                return entities;
            }

            for (File classFile: classFiles) {
                String className = basePackage + "." +
                        classFile.getName().replace(".class", "");

                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Entity.class)) {
                        entities.add(clazz);
                        log.info("Найдена @Entity: {}", clazz.getSimpleName());
                    }
                } catch (ClassNotFoundException e) {
                    log.warn("Не удалось загрузить класс: {}", className);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сканирования пакета: " + basePackage, e);
        }

        log.info("Всего: {} entities", entities.size());
        return entities;
    }

    public void createTables() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            for (Class<?> entity: scanEntities()) {
                String ddl = buildCreateTable(entity);
                log.info("DDL:\n{}", ddl);
                statement.execute(ddl);
            }
        }
    }


    private String buildCreateTable(Class<?> entity) {
        String table = entity.getSimpleName().toLowerCase();
        List<String> columns = new ArrayList<>();

        for (Field field: entity.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                columns.add("    " + field.getName() + " BIGSERIAL PRIMARY KEY");
            } else if (field.isAnnotationPresent(Column.class)) {
                columns.add("   " + field.getName() + " VARCHAR(255)");
            } else if (field.isAnnotationPresent(ManyToOne.class)) {
                columns.add("    " + field.getName() + "_id bigint");
            }
        }

        return "CREATE TABLE IF NOT EXISTS " + table + " (\n"
                + String.join(",\n", columns) + "\n)";
    }

    public void validateSchema() throws SQLException{
        try (Connection connection = dataSource.getConnection()
        ) {
            DatabaseMetaData metaData = connection.getMetaData();

            for (Class<?> entity: scanEntities()) {
                String table = entity.getSimpleName().toLowerCase();

                try (ResultSet rs = metaData.getTables(null, null,
                        table, new String[]{"TABLE"})) {
                    if (!rs.next()) {
                        log.error("[FAIL] Таблица не найдена: {}", table);
                        continue;
                    }
                    log.info("[OK] Таблица: {}", table);
                }

                Set<String> dbColumns = new HashSet<>();
                try (ResultSet rs = metaData.getColumns(null, null,
                        table, null)){
                    while (rs.next()) dbColumns.add(rs.getString("COLUMN_NAME").toLowerCase());
                }

                for (Field field: entity.getDeclaredFields()) {
                    String expected;

                    if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Column.class)) {
                        expected = field.getName().toLowerCase();
                    } else if (field.isAnnotationPresent(ManyToOne.class)) {
                        expected = field.getName().toLowerCase() + "_id";
                    } else continue;

                    if (dbColumns.contains(expected)) {
                        log.info("[OK]   {}.{}", table, expected);
                    } else log.error("[FAIL] {}.{} — отсутствует", table, expected);
                }
            }
        }
    }

}
