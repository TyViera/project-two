package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
@PreAuthorize("isAuthenticated()")
public class PurchaseController {

  private final PurchaseService purchaseService;

  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  @PostMapping
  public ResponseEntity<Void> purchaseProduct(@Valid @RequestBody Purchase purchaseRequest) {
    purchaseService.createPurchase(purchaseRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
