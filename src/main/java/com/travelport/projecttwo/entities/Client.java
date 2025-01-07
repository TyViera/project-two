package com.travelport.projecttwo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

@Schema
@Entity
@Table(name = "clients")
public class Client {
    @Id
    private String id;

    @Column(name = "nif", nullable = false, length = 9, unique = true)
    @Size(min=9, max=10)
    private String nif;

    @Column(name = "name", nullable = false, length = 150)
    @Size(min=2, max=150)
    private String name;

    @Column(name = "address", nullable = true, length = 255)
    @Size(min=1, max = 255)
    private String address;

    public String generateId(){
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
