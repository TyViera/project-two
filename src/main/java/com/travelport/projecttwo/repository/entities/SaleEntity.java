package com.travelport.projecttwo.repository.entities;

import ch.qos.logback.core.net.server.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Table(name = "sales")
public class SaleEntity {

    @Id
    @NotBlank
    @Length(min = 36, max = 36)
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Positive
    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SaleEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
