package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.dto.Error;
import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Tag(name = "products", description = "the products API")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping
    @Operation(
            tags = "products",
            description = "List all products",
            operationId = "getAllProducts"
    )
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation (
            tags = "products",
            description = "Get a product by id",
            operationId = "getProductById",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The product id",
                            in = ParameterIn.PATH,
                            schema = @Schema(implementation = UUID.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Error.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        Optional<Product> product = productService.getProductById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @PostMapping
    @Operation (
            tags = "products",
            description = "Create a new Product",
            operationId = "createProduct",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Product.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    @Operation (
            tags = "products",
            description = "Update a Product",
            operationId = "updateProduct",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description =  "Product created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Product.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        Optional<Product> searchedProduct = productService.getProductById(id);
        if (searchedProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, product).get());
        }
    }

    @DeleteMapping("/{id}")
    @Operation (
            tags = "products",
            description = "Delete Product by id",
            operationId = "deleteProduct",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The Product id",
                            in = ParameterIn.PATH,
                            schema = @Schema(implementation = UUID.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Error.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Product> deleteProduct(@PathVariable UUID id) {
        Optional<Product> searchedProduct = productService.getProductById(id);
        if (searchedProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
