package com.travelport.projecttwo.controllers.dtos.product;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ProductRequestDto {

    @NotBlank
    @Length(min = 5, max = 10)
    private String code;

    @NotBlank
    @Length(min = 2, max = 100)
    private String name;


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
}
