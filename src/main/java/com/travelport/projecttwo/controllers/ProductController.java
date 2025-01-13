package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.services.ProductServiceImpl;
import com.travelport.projecttwo.services.SaleService;
import com.travelport.projecttwo.services.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductServiceImpl productService;

  @Autowired
  private SaleService saleService;

  @Autowired
  private ProductStockService productStockService;

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
    try {
      Product product = productService.read(id);
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    Product createdProduct = productService.create(product);
    ProductStock productStock = new ProductStock();
    productStock.setProduct(createdProduct);
    productStock.setQuantity(0);
    productStockService.create(productStock);

    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
    try {
      Product updatedProduct = productService.update(id, product);
      return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
    try {
      boolean hasBeenSold = saleService.hasSales(id);

      if (hasBeenSold) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
      }

      ProductStock productStock = productStockService.getStockByProductId(id);
      if (productStock != null && productStock.getQuantity() > 0) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
      }

      productService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
