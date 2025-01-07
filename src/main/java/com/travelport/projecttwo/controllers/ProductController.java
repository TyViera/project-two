package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.controllers.dtos.ProductRequestDto;
import com.travelport.projecttwo.controllers.dtos.ProductResponseDto;
import com.travelport.projecttwo.controllers.mappings.ProductMappings;
import com.travelport.projecttwo.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        var products = productService.getProducts();

        return ResponseEntity.ok(ProductMappings.toDto(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable String id) {
        var productDomain = productService.getProductById(id);

        if (productDomain.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ProductMappings.toDto(productDomain.get()));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> postProduct(@Validated @RequestBody ProductRequestDto productRequest) {
        var productDomain = ProductMappings.toDomain(productRequest);

        var savedProduct = productService.createProduct(productDomain);

        URI location = URI.create("/products/" + productDomain.getId());

        return ResponseEntity.created(location).body(ProductMappings.toDto(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> putProduct(@PathVariable String id, @Validated @RequestBody ProductRequestDto productRequest) {
        var productDomain = ProductMappings.toDomain(productRequest);

        try {
            var savedProduct = productService.updateProduct(id, productDomain);
            return ResponseEntity.ok(ProductMappings.toDto(savedProduct));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
