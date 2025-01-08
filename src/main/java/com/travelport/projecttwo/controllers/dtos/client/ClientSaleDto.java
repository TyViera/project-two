package com.travelport.projecttwo.controllers.dtos.client;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ClientSaleDto {

    @NotBlank
    @Length(min = 36, max = 36)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
