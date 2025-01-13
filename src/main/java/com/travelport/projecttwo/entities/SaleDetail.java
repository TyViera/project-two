package com.travelport.projecttwo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Schema
@IdClass(SaleDetail.SaleDetailPK.class)
@Table(name = "sales_det")
public class SaleDetail {
    @Id
    @Column(name = "sale_id", nullable = false)
    private String saleId;

    @Id
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    public SaleDetail() {}

    public SaleDetail(String saleId, String productId, Integer quantity, Product product, Sale sale){
        this.saleId=saleId;
        this.productId=productId;
        this.quantity=quantity;
        this.product=product;
        this.sale=sale;
    }

    public static class SaleDetailPK implements Serializable {
        protected String saleId;
        protected String productId;

        public SaleDetailPK() {}

        public SaleDetailPK(String saleId, String productId) {
            this.saleId = saleId;
            this.productId = productId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SaleDetailPK that)) return false;
            return Objects.equals(saleId, that.saleId) && Objects.equals(productId, that.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(saleId, productId);
        }
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleDetail that)) return false;
        return Objects.equals(saleId, that.saleId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, productId);
    }
}