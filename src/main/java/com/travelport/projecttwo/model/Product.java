package com.travelport.projecttwo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Schema
@Entity
public class Product {

    @Id
    @Schema( description = "Product ID")
    private UUID productId = UUID.randomUUID();

    @NotBlank
    @Length(min = 2, max = 100)
    @Schema(description = "Product Name")
    private String name;

    @NotBlank
    @Length(min = 5, max = 10)
    @Column(unique = true)
    private String code;

    @Min(0)
    private Integer stock = 0;

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
