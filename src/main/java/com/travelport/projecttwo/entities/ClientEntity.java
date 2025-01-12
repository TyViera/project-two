package com.travelport.projecttwo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "clients")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
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

    public ClientEntity(String name, String nif, String address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.nif = nif;
        this.address = address;
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
