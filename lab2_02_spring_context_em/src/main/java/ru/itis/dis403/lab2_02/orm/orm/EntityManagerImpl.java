package ru.itis.dis403.lab2_02.orm.orm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itis.dis403.lab2_02.orm.annotation.Column;
import ru.itis.dis403.lab2_02.orm.annotation.Id;
import ru.itis.dis403.lab2_02.orm.annotation.ManyToOne;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@RequiredArgsConstructor
public class EntityManagerImpl implements EntityManager, Closeable {

    private final Connection connection;

    private String tableName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    private Field idField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .peek(f -> f.setAccessible(true))
                .findFirst()
                .orElseThrow(() ->  new RuntimeException("@Id не найден: " + clazz));
    }

    private List<Field> dataField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class)
                        || f.isAnnotationPresent(ManyToOne.class))
                .peek(f -> f.setAccessible(true))
                .toList();
    }

    private String colName(Field field) {
        return field.isAnnotationPresent(ManyToOne.class)
                ? field.getName() + "_id"
                : field.getName();
    }

    private Object colValue(Field field, Object entity) throws IllegalAccessException {
        Object val = field.get(entity);
        if (field.isAnnotationPresent(ManyToOne.class) && val != null) {
            Field relId = idField(val.getClass());
            return relId.get(val);
        }
        return val;
    }

    @Override
    public <T> T save(T entity) {
       try {
            Field idField = idField(entity.getClass());
            return idField.get(entity) == null
                    ? insert(entity, idField)
                    : update(entity, idField);
       } catch (Exception e) {
           throw new RuntimeException("save failed", e);
       }
    }

    private <T> T insert(T entity, Field idField) throws Exception {
        List<Field> fields = dataField(entity.getClass());
        StringJoiner cols = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");
        fields.forEach(f -> { cols.add(colName(f)); placeholders.add("?"); });

        String sql = "INSERT INTO " + tableName(entity.getClass())
                + " (" + cols + ") VALUES (" + placeholders + ")";
        log.debug("SQL: {}", sql);

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < fields.size(); i++) {
                ps.setObject(i + 1, colValue(fields.get(i), entity));
            }
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) idField.set(entity, keys.getLong(1));
            }
        }
        return entity;
    }

    private <T> T update(T entity, Field idField) throws Exception {
        List<Field> fields = dataField(entity.getClass());
        StringJoiner sets = new StringJoiner(", ");
        fields.forEach(f -> sets.add(colName(f) + " = ?"));

        String sql = "UPDATE " + tableName(entity.getClass()) + " SET "
                + sets + " WHERE " + idField.getName() + " = ?";
        log.debug("SQL: {}", sql);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            for (Field field: fields) {
                ps.setObject(i++, colValue(field, entity));
            }
            ps.setObject(i, idField.get(entity));
            ps.executeUpdate();
        }
        return entity;
    }


    @Override
    public void remove(Object entity){
        try {
            Field idField = idField(entity.getClass());
            String sql = "DELETE FROM " + tableName(entity.getClass())
                    + " WHERE " + idField.getName() + " = ?";
            log.debug("SQL: {}", sql);
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setObject(1, idField.get(entity));
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("remove failed", e);
        }
    }

    @Override
    public <T> T find(Class<T> entityType, Object key) {
        try {
            Field idField = idField(entityType);
            String sql = "SELECT * FROM "  + tableName(entityType)
                    + " WHERE " + idField.getName() + " = ?";

            log.debug("SQL: {}", sql);
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setObject(1, key);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? mapRow(entityType, rs) : null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("find failed", e);
        }
    }

    @Override
    public <T> List<T> findAll(Class<T> entityType) {
        try {
            String sql = "SELECT * FROM " + tableName(entityType);
            log.debug("SQL: {}", sql);
            List<T> result = new ArrayList<>();
            try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ){
                while (rs.next()) {
                    result.add(mapRow(entityType, rs));
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("findAll failed", e);
        }
    }

    private <T> T mapRow(Class<T> clazz, ResultSet rs) throws Exception {
        T obj = clazz.getDeclaredConstructor().newInstance();

        for (Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Column.class)) {
                Object val = rs.getObject(field.getName());

                if (val instanceof Integer && field.getType() == Long.class) {
                    val = ((Integer) val).longValue();
                }
                field.set(obj, val);
            } else if (field.isAnnotationPresent(ManyToOne.class)) {
                Object fkVal = rs.getObject(field.getName() + "_id");

                if (fkVal != null) {
                    field.set(obj, find(field.getType(), fkVal));
                }
            }
        }
        return obj;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
