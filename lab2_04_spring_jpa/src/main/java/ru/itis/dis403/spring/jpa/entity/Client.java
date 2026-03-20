package ru.itis.dis403.spring.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client extends Person {

    private String address;
}
