package com.travelport.projecttwo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Schema
@Entity
public class PurchaseDetail {

    @Id
    @Schema( description = "PurchaseDetail ID")
    private UUID purchaseDetailId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchaseId;

    @ManyToOne
    @JoinColumn(name= "productId", nullable = false)
    private Product productId;

    private Integer quantity;

    public UUID getPurchaseDetailId() { return purchaseDetailId; }
    public void setPurchaseDetailId(UUID purchaseDetailId) { this.purchaseDetailId = purchaseDetailId; }

    public Purchase getPurchaseId() { return purchaseId; }
    public void setPurchaseId(Purchase purchaseId) { this.purchaseId = purchaseId; }

    public Product getProductId() { return productId; }
    public void setProductId(Product productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
