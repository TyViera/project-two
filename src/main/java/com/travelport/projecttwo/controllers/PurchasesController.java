package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.controllers.dtos.purchase.PurchaseRequestDto;
import com.travelport.projecttwo.services.IPurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    private final IPurchaseService purchaseService;

    public PurchasesController(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity createPurchase(@Validated @RequestBody PurchaseRequestDto purchaseRequest) {
        try {
            purchaseService.createPurchase(purchaseRequest);
            URI location = URI.create("/purchases");
            return ResponseEntity.created(location).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
