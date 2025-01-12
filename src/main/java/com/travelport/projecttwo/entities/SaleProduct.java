package com.travelport.projecttwo.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sales_products")
public class SaleProduct {

    @EmbeddedId
    private SaleProductId id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @MapsId("saleId")
    @JoinColumn(name = "sale_id", nullable = false)
    private SaleEntity sale;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    public SaleProduct() {}

    public SaleProduct(SaleEntity sale, ProductEntity product, int quantity) {
        this.id = new SaleProductId(sale.getId(), product.getId());
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
    }

    public SaleProductId getId() {
        return id;
    }

    public void setId(SaleProductId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SaleEntity getSale() {
        return sale;
    }

    public void setSale(SaleEntity sale) {
        this.sale = sale;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleProduct that = (SaleProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

