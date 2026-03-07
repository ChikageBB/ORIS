package ru.itis.dis403.lab2_02.orm.orm;


import java.io.Closeable;
import java.util.List;

public interface EntityManager extends Closeable {
    <T> T save(T entity);

    void remove(Object entity);

    <T> T find(Class<T> entityType, Object key);

    <T> List<T> findAll(Class<T> entityType);
}
