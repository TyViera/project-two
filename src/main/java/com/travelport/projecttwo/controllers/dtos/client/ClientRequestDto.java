package com.travelport.projecttwo.controllers.dtos.client;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ClientRequestDto {

    @NotBlank
    @Length(min = 9, max = 10)
    private String nif;

    @NotBlank
    @Length(min = 2, max = 150)
    private String name;

    @Length(min = 5, max = 150)
    private String address;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
