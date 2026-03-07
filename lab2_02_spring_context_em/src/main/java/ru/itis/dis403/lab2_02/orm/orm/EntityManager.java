package ru.itis.dis403.lab2_02.orm;


public interface EntityManager {
    <T> T save(T entity);

    void remove(Object entity);
}
