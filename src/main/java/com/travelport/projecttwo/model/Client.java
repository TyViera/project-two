package com.travelport.projecttwo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    private final String id;

    @NotNull
    @Size(min = 2, max = 150)
    private String name;

    @NotNull
    @Size(min = 9, max = 10)
    private String nif;

    @Size(min = 5, max = 150)
    private String address;

    public Client(String id,
                  @NotNull @Size(min = 2, max = 150) String name,
                  @NotNull @Size(min = 9, max = 10) String nif,
                  @NotNull @Size(min = 9, max = 10) String address) {
        this.id = id;
        this.name = name;
        this.nif = nif;
    }

    public Client(@NotNull @Size(min = 2, max = 150) String name,
                  @NotNull @Size(min = 9, max = 10) String nif) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.nif = nif;
    }
    public Client(@NotNull @Size(min = 2, max = 150) String name,
                  @NotNull @Size(min = 9, max = 10) String nif,
                  @Size(min = 5, max = 150) String address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.nif = nif;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public @NotNull @Size(min = 2, max = 150) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2, max = 150) String name) {
        this.name = name;
    }

    public @NotNull @Size(min = 9, max = 10) String getNif() {
        return nif;
    }

    public void setNif(@NotNull @Size(min = 9, max = 10) String nif) {
        this.nif = nif;
    }

    public @Size(min = 5, max = 150) String getAddress() {
        return address;
    }

    public void setAddress(@Size(min = 5, max = 150) String address) {
        this.address = address;
    }
}
