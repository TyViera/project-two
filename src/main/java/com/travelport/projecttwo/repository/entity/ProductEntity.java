package com.travelport.projecttwo.repository.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelport.projecttwo.controller.model.ProductRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductEntity {

  public ProductEntity() {}

  public ProductEntity(ProductRequest inputProduct) {
    this.id = UUID.randomUUID().toString();
    this.name = inputProduct.getName();
    this.code = inputProduct.getCode();
    this.stock = 0;
  }

  @Id
  private String id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "code", length = 10, nullable = false)
  private String code;

  @Column(name = "stock", nullable = false)
  private Integer stock;

  public @NotNull String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductEntity that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

}
