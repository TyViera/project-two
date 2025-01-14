package com.travelport.projecttwo.repository.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @NotBlank
    @Length(min = 36, max = 36)
    private String id;

    @NotBlank
    @Length(min = 5, max = 10)
    private String code;

    @NotBlank
    @Length(min = 2, max = 100)
    private String name;

    @NotNull
    @PositiveOrZero
    private int stock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
