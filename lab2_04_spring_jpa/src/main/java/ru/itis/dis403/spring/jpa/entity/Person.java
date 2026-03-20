package ru.itis.dis403.spring.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ToString(exclude = {"phones"})
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    @ManyToOne
    protected Phone phone;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    protected Set<Phone> phones = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Phone)) return false;
        Phone phone = (Phone) obj;
        return Objects.equals(phone, phone.getNumber())
                && Objects.equals(name, ((Phone) obj).getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phone);
    }
}
