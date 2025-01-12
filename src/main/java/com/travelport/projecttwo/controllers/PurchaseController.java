package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.entities.ProductPurchase;
import com.travelport.projecttwo.services.PurchaseServiceImpl;
import com.travelport.projecttwo.requests.PurchaseRequest;
import com.travelport.projecttwo.requests.ProductPurchaseRequest;
import com.travelport.projecttwo.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

  @Autowired
  private PurchaseServiceImpl purchaseService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<Void> purchaseProduct(@RequestBody PurchaseRequest purchaseRequest) {
    try {
      List<ProductPurchase> productPurchases = purchaseRequest.getProducts().stream()
          .map(this::convertToProductPurchase)
          .collect(Collectors.toList());

      purchaseService.purchaseProduct(purchaseRequest.getSupplier(), productPurchases);

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private ProductPurchase convertToProductPurchase(ProductPurchaseRequest request) {
    Product product = new Product();
    product.setId(request.getId());

    ProductPurchase productPurchase = new ProductPurchase();
    productPurchase.setProduct(product);
    productPurchase.setQuantity(request.getQuantity());

    return productPurchase;
  }
}
