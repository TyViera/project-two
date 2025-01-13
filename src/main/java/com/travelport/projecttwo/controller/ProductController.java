package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products")
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products", description = "Retrieves a list of all products in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
                    content = @Content(
                            schema = @Schema(type="array", implementation = Product.class)
                    ))
    })
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @Operation(summary = "Find product", description = "Finds product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful",
                    content = @Content(
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById( @Parameter(name = "id", description = "ID of product to return", required = true, in = ParameterIn.PATH) @PathVariable("id") String id){
        Product product;
        try{
            product = productService.getProductById(id);
            return new ResponseEntity<>(
                    product,
                    HttpStatus.OK
            );
        }catch(EntityNotFoundException e) {
            return new ResponseEntity<>(
                    "Product not found",
                    HttpStatus.NOT_FOUND
            );
        }

    }

    @Operation(summary = "Create a new product", description = "Adds a new product to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "406", description = "Product not saved",
                    content = @Content())
    })
    @PostMapping
    public ResponseEntity<Object> postProduct(@RequestBody Product product){
        try {
            Product newProduct=productService.addProduct(product);
            return new ResponseEntity<>(
                    newProduct,
                    HttpStatus.CREATED
            );
        }catch(Exception e){
            return new ResponseEntity<>(
                    "Product not saved",
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @Operation(summary = "Update product", description = "Updates an existing product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable("id") String id, @RequestBody Product product){
        try {
            var productUpdated=productService.updateProduct(id, product);
            return new ResponseEntity<>(
                    productUpdated,
                    HttpStatus.OK
            );
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(
                    "Product not found",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Operation(summary = "Delete product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Product deleted successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "422", description = "Product has associated sales and cannot be deleted",
                    content = @Content(
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred",
                    content = @Content(
                            schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable("id") String id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Operation successful", HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>("Exists a sales in the system for this product", HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
