package com.travelport.projecttwo.repository.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @NotNull
    @NotBlank
    @Length(min = 9, max = 10)
    private String nif;

    @NotNull
    @NotBlank
    @Length(min = 2, max = 150)
    private String name;

    @Length(min = 5, max = 150)
    private String address;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientEntity client)) return false;
        return Objects.equals(nif, client.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nif);
    }
}
