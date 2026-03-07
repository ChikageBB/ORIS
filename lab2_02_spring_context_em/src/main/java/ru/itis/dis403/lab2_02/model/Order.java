package ru.itis.dis403.lab2_02.model;

import lombok.Data;

@Data
public class Order {
    private Product product;
    private Integer count;


    public Order() {

    }


    public Order(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }
}
