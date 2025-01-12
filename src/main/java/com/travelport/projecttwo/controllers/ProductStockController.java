package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.responses.StockResponse;
import com.travelport.projecttwo.services.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductStockController {

  @Autowired
  private ProductStockService productStockService;

  @GetMapping("/{id}/stock")
  public ResponseEntity<?> getProductStock(@PathVariable UUID id) {
    ProductStock productStock = productStockService.getStockByProductId(id);

    if (productStock == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().body(
        new StockResponse(productStock.getQuantity())
    );
  }
}
