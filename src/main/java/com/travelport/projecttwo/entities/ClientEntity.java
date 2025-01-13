package com.travelport.projecttwo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    private String id;

    private String name;

    private String nif;

    private String address;

    public ClientEntity(String id, String name, String nif, String address) {
        this.id = id;
        this.name = name;
        this.nif = nif;
        this.address = address;
    }

    public ClientEntity() {
        this.id = UUID.randomUUID().toString();
    }

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
