package com.travelport.projecttwo.repository.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sales_details")
@IdClass(SaleDetailEntity.SaleDetailId.class)
public class SaleDetailEntity {

  @Id
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "sale_id", nullable = false)
  private SaleEntity sale;

  @Id
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  public SaleDetailEntity() {}

  public SaleDetailEntity(SaleEntity sale, ProductEntity product, Integer quantity) {
    this.sale = sale;
    this.product = product;
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

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public static class SaleDetailId implements Serializable {
    private SaleEntity sale;
    private ProductEntity product;

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
      if (!(o instanceof SaleDetailId that)) return false;
      return Objects.equals(sale, that.sale) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
      return Objects.hash(sale, product);
    }
  }

}
