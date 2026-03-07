package ru.itis.dis403.lab2_02.orm.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dis403.lab2_02.orm.annotation.Column;
import ru.itis.dis403.lab2_02.orm.annotation.Entity;
import ru.itis.dis403.lab2_02.orm.annotation.Id;
import ru.itis.dis403.lab2_02.orm.annotation.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Street {

    @Id
    private Long id;

    @Column
    private String name;

    @ManyToOne
    private City city;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
