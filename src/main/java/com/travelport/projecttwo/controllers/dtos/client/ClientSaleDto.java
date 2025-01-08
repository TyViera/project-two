package com.travelport.projecttwo.controllers.dtos.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClientSaleDto {

    @NotBlank
    @Size(min = 36, max = 36)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
