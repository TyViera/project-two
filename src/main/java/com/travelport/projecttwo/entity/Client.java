package com.travelport.projecttwo.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Id;
import java.util.UUID;
import jakarta.persistence.Column;

@Entity
@Data
public class Client {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String nif;

    @Column(length = 150)
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
