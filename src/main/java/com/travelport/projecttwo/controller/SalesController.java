package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.SaleRequest;
import com.travelport.projecttwo.service.impl.SalesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping
    public ResponseEntity<Void> sellProduct(@Valid @RequestBody SaleRequest saleRequest) {
        salesService.sellProduct(saleRequest);
        return ResponseEntity.status(201).build();
    }
}
