package com.travelport.projecttwo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Schema(description = "Client entity for CRUD operations")
public class Client {

    @Schema(description = "Client ID (UUID)", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private String id;

    @NotNull
    @Length(min = 2, max = 150)
    @Schema(description = "Client name", example = "John Doe", minLength = 2, maxLength = 150)
    private String name;

    @NotNull
    @Length(min = 9, max = 10)
    @Schema(description = "Client NIF (unique identifier)", example = "123456789", minLength = 9, maxLength = 10)
    private String nif;

    @Length(min = 5, max = 150)
    @Schema(description = "Client address (optional)", example = "123 Main Street, City, Country", minLength = 5, maxLength = 150)
    private String address;


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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}