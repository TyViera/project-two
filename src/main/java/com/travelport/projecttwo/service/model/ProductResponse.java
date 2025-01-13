package com.travelport.projecttwo.service.model;


import com.travelport.projecttwo.repository.entity.ProductEntity;

public class ProductResponse {

  private String id;
  private String name;
  private String code;

  public ProductResponse(ProductEntity productEntity) {
    this.id = productEntity.getId();
    this.name = productEntity.getName();
    this.code = productEntity.getCode();
  }

  public String getId() {
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
}
