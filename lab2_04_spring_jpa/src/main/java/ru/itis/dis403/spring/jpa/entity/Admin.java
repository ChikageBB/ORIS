package ru.itis.dis403.spring.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
@Table(name = "admin")
public class Admin extends Person {

    private String email;
}
