package ru.itis.dis403.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itis.dis403.spring.jpa.entity.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
