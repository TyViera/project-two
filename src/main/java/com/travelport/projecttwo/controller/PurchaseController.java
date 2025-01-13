package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.Purchase;
import com.travelport.projecttwo.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<?> createPurchase(@RequestBody Purchase purchase) {
        try {
            Purchase savedPurchase = purchaseService.save(purchase);
            return ResponseEntity.status(201).body(savedPurchase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
