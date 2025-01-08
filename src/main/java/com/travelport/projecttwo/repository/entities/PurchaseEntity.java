package com.travelport.projecttwo.repository.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "purchases")
public class PurchaseEntity {

    @Id
    @NotBlank
    @Length(min = 36, max = 36)
    private String id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @NotBlank
    @Length(min = 2, max = 50)
    private String supplierName;

    @Positive
    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
