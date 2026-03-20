package ru.itis.dis403.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.dis403.spring.jpa.entity.Phone;

import java.util.List;


@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    @Query("select p from Phone p where p.number like :num")
    List<Phone> getPhoneLike(@Param("num") String num);

    List<Phone> findByNumberLike(String num);

}
