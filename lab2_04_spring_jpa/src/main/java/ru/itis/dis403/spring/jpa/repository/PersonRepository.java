package ru.itis.dis403.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dis403.spring.jpa.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
