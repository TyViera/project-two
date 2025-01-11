package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.PurchaseRequest;
import com.travelport.projecttwo.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//TODO: maybe this could be only @Controller
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<String> purchaseProduct(@RequestBody PurchaseRequest purchaseRequest){
        try{
            purchaseService.renewStock(purchaseRequest);
            return new ResponseEntity<>("Product successfully purchased", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Purchase was not made", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
