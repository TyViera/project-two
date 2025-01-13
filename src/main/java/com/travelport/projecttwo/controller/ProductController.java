package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.controller.model.ProductRequest;
import com.travelport.projecttwo.dto.ErrorResponse;
import com.travelport.projecttwo.exception.DeletingProductException;
import com.travelport.projecttwo.exception.DuplicatedCodeException;
import com.travelport.projecttwo.service.ProductService;
import com.travelport.projecttwo.service.model.ProductResponse;
import com.travelport.projecttwo.service.model.StockResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "products", description = "Operations about products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @Operation(
      operationId = "getProducts",
      summary = "Get all products",
      description = "Get all products in a List",
      tags = { "products" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation - products list", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))
          })
      }
  )
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAll () {
    var productList = productService.getAll();
    return ResponseEntity.ok(productList);
  }

  @Operation(
      operationId = "createProduct",
      summary = "Create a product",
      description = "",
      tags = { "products" },
      responses = {
          @ApiResponse(responseCode = "201", description = "successful operation", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
          }),
          @ApiResponse(responseCode = "422", description = "There is already a product with the provided code")
      }
  )
  @PostMapping
  public ResponseEntity<?> save (@RequestBody ProductRequest inputProduct) {
    try {
      var createdProduct = productService.save(inputProduct);
      return ResponseEntity.status(201).body(createdProduct);

    } catch (DuplicatedCodeException e) {
      return ResponseEntity.status(422).body(new ErrorResponse(e.getMessage()));
    }

  }

  @Operation(
      operationId = "getProductById",
      summary = "Get product by id",
      description = "Get one product by id",
      tags = { "products" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
          }),
          @ApiResponse(responseCode = "404", description = "Product not found")
      }
  )
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getById (@PathVariable("id") String id) {
    var foundProduct = productService.getById(id);
    if (foundProduct.isEmpty()) return ResponseEntity.notFound().build();

    return ResponseEntity.ok(foundProduct.get());
  }

  @Operation(
      operationId = "updateProduct",
      summary = "Update product",
      description = "Update a product by id",
      tags = { "products" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation - returns the updated product", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
          }),
          @ApiResponse(responseCode = "404", description = "product not found")
      }
  )
  @PutMapping("/{id}")
  public ResponseEntity<?> updateById (@PathVariable("id") String id, @RequestBody ProductRequest inputProduct) {
    try {
      var updatedProduct = productService.updateById(id, inputProduct);
      if (updatedProduct.isEmpty()) return ResponseEntity.notFound().build();
      return ResponseEntity.ok(updatedProduct.get());
    } catch (DuplicatedCodeException e) {
      return ResponseEntity.status(422).body(new ErrorResponse(e.getMessage()));
    }
  }

  @Operation(
      operationId = "deleteProduct",
      summary = "Delete product",
      description = "Delete a product by id",
      tags = { "products" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation"),
          @ApiResponse(responseCode = "404", description = "Product not found"),
          @ApiResponse(responseCode = "422", description = "Product has been sold, cannot be deleted")
      }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteById (@PathVariable("id") String id) {
    try {
      var isProductDeleted = productService.deleteById(id);
      if (!isProductDeleted) return ResponseEntity.notFound().build();
      return ResponseEntity.ok().build();
    } catch (DeletingProductException e) {
      return ResponseEntity.status(422).body(new ErrorResponse(e.getMessage()));
    }
  }

  @Operation(
      operationId = "getProductStock",
      summary = "See stock",
      description = "See stock of a product by id",
      tags = { "products" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = StockResponse.class))
          }),
          @ApiResponse(responseCode = "404", description = "product not found")
      }
  )
  @GetMapping("/{id}/stock")
  public ResponseEntity<StockResponse> getStockById (@PathVariable("id") String id) {
    var stock = productService.getStockById(id);
    if (stock.isEmpty()) return ResponseEntity.notFound().build();

    return ResponseEntity.ok(stock.get());
  }

}
