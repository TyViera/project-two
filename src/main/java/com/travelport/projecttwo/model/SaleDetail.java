package com.travelport.projecttwo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Schema
@Entity
public class SaleDetail {
    @Id
    @Schema( description = "SaleDetail ID")
    private UUID salesDetailId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale saleId;

    @ManyToOne
    @JoinColumn(name= "productId", nullable = false)
    private Product productId;

    private Integer quantity;

    public UUID getSalesDetailId() { return salesDetailId; }
    public void setSalesDetailId(UUID salesDetailId) { this.salesDetailId = salesDetailId; }

    public Sale getSaleId() { return saleId; }
    public void setSaleId(Sale saleId) { this.saleId = saleId; }

    public Product getProductId() { return productId; }
    public void setProductId(Product productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity;}
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
