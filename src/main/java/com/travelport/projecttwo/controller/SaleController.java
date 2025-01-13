package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
@PreAuthorize("isAuthenticated()")
public class SaleController {

  private final SaleService saleService;

  public SaleController(SaleService saleService) {
    this.saleService = saleService;
  }

  @PostMapping
  public ResponseEntity<Void> sellProduct(@Valid @RequestBody Sale sale) {
    boolean success = saleService.createSale(sale);
    if (!success) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Not enough stock
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/most-sold-products")
  public ResponseEntity<Object> getMostSoldProducts() {
    return ResponseEntity.ok(saleService.getMostSoldProducts());
  }
}
