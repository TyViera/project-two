package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.MostSoldProductResponse;
import com.travelport.projecttwo.entities.SaleRequest;
import com.travelport.projecttwo.entities.SaleResponse;
import com.travelport.projecttwo.service.impl.SalesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


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

    @GetMapping("/{id}/sales")
    public List<SaleResponse> getSalesByClientId(@PathVariable UUID id) {
        return salesService.getSalesByClientId(id);
    }

    @GetMapping("/most-sold-products")
    public List<MostSoldProductResponse> getMostSoldProducts() {
        return salesService.getMostSoldProducts();
    }


}
