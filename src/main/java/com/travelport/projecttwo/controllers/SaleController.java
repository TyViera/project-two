package com.travelport.projecttwo.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelport.projecttwo.entities.ProductSale;
import com.travelport.projecttwo.requests.ProductSaleRequest;
import com.travelport.projecttwo.requests.SaleRequest;
import com.travelport.projecttwo.services.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {

  @Autowired
  private SaleService saleService;

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> sellProduct(@RequestBody SaleRequest request) {
    try {
      UUID clientId = request.getClient().getId();
      List<ProductSaleRequest> products = request.getProducts();

      saleService.sellProduct(clientId, products);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/most-sold-products")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<Object>> getTop5MostSoldProducts() {
    List<ProductSale> productSales = saleService.getTop5MostSoldProducts();

    List<Object> response = productSales.stream()
        .map(productSale -> {
          return Map.of(
              "product", Map.of(
                  "id", productSale.getProduct().getId().toString(),
                  "name", productSale.getProduct().getName()
              ),
              "quantity", productSale.getQuantity()
          );
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }
}
