package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.ProductEntity;
import com.travelport.projecttwo.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieve a list of all products",
            tags = {"products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products retrieved")
    })
    @GetMapping
    public List<ProductEntity> getClients() {
        return productService.getProducts();
    }

    @Operation(
            summary = "Get product by ID",
            description = "Retrieve a specific product by its ID",
            tags = {"products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable String id) {
        Optional<ProductEntity> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new product",
            description = "Create a new product entry",
            tags = {"products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully")
    })
    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productService.createProduct(product);
    }

    @Operation(
            summary = "Update an existing product",
            description = "Update details of a specific product by ID",
            tags = {"products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable String id, @RequestBody ProductEntity product) {
        Optional<ProductEntity> updatedProduct = productService.updateProduct(id, product);
        return updatedProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a product",
            description = "Delete a product by its ID. Returns 404 if the product is not found and 422 if it has been sold.",
            tags = {"products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "422", description = "Product has been sold")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
        } catch (NullPointerException e) { // 404  if product not found
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) { // 422 if product has been sold
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().build();
    }
}

