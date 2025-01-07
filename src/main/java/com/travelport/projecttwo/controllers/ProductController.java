package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.controllers.dtos.ProductRequestDto;
import com.travelport.projecttwo.controllers.dtos.ProductResponseDto;
import com.travelport.projecttwo.controllers.mappings.ProductMappings;
import com.travelport.projecttwo.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> postProduct(@Validated @RequestBody ProductRequestDto productRequest) {
        var productDomain = ProductMappings.toDomain(productRequest);

        var savedProduct = productService.createProduct(productDomain);

        URI location = URI.create("/products/" + productDomain.getId());

        return ResponseEntity.created(location).body(ProductMappings.toDto(savedProduct));
    }
}
