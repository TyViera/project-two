package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductPurchase;
import com.travelport.projecttwo.services.PurchaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

  @Autowired
  private PurchaseServiceImpl purchaseService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<Void> purchaseProduct(
      @RequestParam UUID productId,
      @RequestParam String supplier,
      @RequestParam int quantity) {

    try {
      if (quantity <= 0) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      Product product = new Product();
      product.setId(productId);

      ProductPurchase productPurchase = new ProductPurchase();
      productPurchase.setProduct(product);
      productPurchase.setQuantity(quantity);

      purchaseService.purchaseProduct(supplier, List.of(productPurchase));

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
