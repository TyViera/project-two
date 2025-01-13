package com.travelport.projecttwo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Schema(description = "Product entity representing a product in the store")
public class Product {

    @Schema(description = "Product ID", example = "uuid")
    private String id;

    @NotNull
    @Length(min = 2, max = 100)
    @Schema(description = "Product name", example = "Laptop", minLength = 2, maxLength = 100)
    private String name;

    @NotNull
    @Length(min = 5, max = 10)
    @Schema(description = "Product code", example = "LAP12345", minLength = 5, maxLength = 10)
    private String code;

    @Schema(description = "Stock quantity of the product", example = "0")
    private int stock = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
