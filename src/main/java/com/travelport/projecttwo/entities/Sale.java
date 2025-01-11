package com.travelport.projecttwo.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Sale(){}

    public Sale(String id, Client client){
        this.id=id;
        this.client=client;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale sale)) return false;
        return Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
