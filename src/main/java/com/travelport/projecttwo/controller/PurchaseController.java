package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.services.PurchaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public Purchase addPurchase(@RequestBody Purchase purchase) {
        return purchaseService.addPurchase(purchase);
    }
}
