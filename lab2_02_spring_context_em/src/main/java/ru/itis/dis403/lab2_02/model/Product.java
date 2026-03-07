package ru.itis.dis403.lab2_02.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
@Data
public class Product {
    private String name;
    private String article;
    private BigDecimal price;
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(article, product.article);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(article);
    }


}
