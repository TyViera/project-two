package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products")
@RestController//TODO: maybe this could be only @Controller
@RequestMapping("/products")
public class ProductController {
    // TODO: catch errors from the service layer and transform them into http responses
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById( @Parameter(name = "id", description = "ID of product to return", required = true, in = ParameterIn.PATH) @PathVariable("id") String id){
        Product product;
        try{
            product = productService.getProductById(id);

        }catch(EntityNotFoundException e) {
            return new ResponseEntity<>(
                    "Product not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                product,
                HttpStatus.OK
        );
    }

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
