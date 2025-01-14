package com.travelport.projecttwo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Schema
@Entity
public class Sale {

    @Id
    @Schema( description = "Sale ID")
    private UUID saleId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client clientId;

    @OneToMany(mappedBy = "saleId")
    private List<SaleDetail> products;

    public UUID getSaleId() { return saleId; }
    public void setSaleId(UUID saleId) { this.saleId = saleId; }

    public Client getClientId() { return clientId; }
    public void setClientId(Client clientId) { this.clientId = clientId; }

    public List<SaleDetail> getProducts() { return products; }
    public void setProducts(List<SaleDetail> products) { this.products = products; }
}
