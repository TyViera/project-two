package com.travelport.projecttwo.repository.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales_cab")
public class SaleCabEntity {

    @Id
    @Length(min = 36, max = 36)
    @NotBlank
    private String id;

    @Column(name = "client_id", nullable = false)
    @Length(min = 36, max = 36)
    @NotBlank
    private String clientId;

    @OneToMany(mappedBy = "saleCab", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleDetEntity> details = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<SaleDetEntity> getDetails() {
        return details;
    }

    public void setDetails(List<SaleDetEntity> details) {
        this.details = details;
    }
}
