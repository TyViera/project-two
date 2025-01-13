package com.travelport.projecttwo.entities;

import jakarta.persistence.*;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, length = 100)
  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
  private String name;

  @Column(nullable = false, unique = true, length = 10)
  @NotBlank(message = "Code is mandatory")
  @Size(min = 5, max = 10, message = "Code must be between 5 and 10 characters")
  private String code;

  public Product() {}

  public Product(String name, String code) {
    this.name = name;
    this.code = code;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
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

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", code='" + code + '\'' +
        '}';
  }
}