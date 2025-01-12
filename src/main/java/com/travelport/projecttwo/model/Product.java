package com.travelport.projecttwo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class Product {

    private final String id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Size(min = 5, max = 10)
    //Unique value
    private String code;

    public Product(@NotNull @Size(min = 2, max = 100) String name,
                  @NotNull @Size(min = 5, max = 10) String code) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public @NotNull @Size(min = 2, max = 100) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2, max = 100) String name) {
        this.name = name;
    }

    public @NotNull @Size(min = 5, max = 10) String getCode() {
        return code;
    }

    public void setCode(@NotNull @Size(min = 5, max = 10) String code) {
        this.code = code;
    }
}
