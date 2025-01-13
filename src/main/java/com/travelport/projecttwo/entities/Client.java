package com.travelport.projecttwo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 150)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name must be alphanumeric")
    private String name;

    @NotBlank
    @Size(min = 9, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name must be alphanumeric")
    private String nif;

    @Size(min = 5, max = 150)
    private String address;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 2, max = 150) @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name must be alphanumeric") String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 2, max = 150) @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name must be alphanumeric") String name) {
        this.name = name;
    }

    public @NotBlank @Size(min = 9, max = 10) @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name must be alphanumeric") String getNif() {
        return nif;
    }

    public void setNif(@NotBlank @Size(min = 9, max = 10) @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name must be alphanumeric") String nif) {
        this.nif = nif;
    }

    public @Size(min = 5, max = 150) String getAddress() {
        return address;
    }

    public void setAddress(@Size(min = 5, max = 150) String address) {
        this.address = address;
    }
}
