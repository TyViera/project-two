package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.MostSoldProductResponse;
import com.travelport.projecttwo.entities.SaleRequest;
import com.travelport.projecttwo.entities.SaleResponse;
import com.travelport.projecttwo.service.impl.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/sales")
@Tag(name = "Sales", description = "Sales management APIs")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping
    @Operation(summary = "Sell a product", description = "Sell a product to a client")
    public ResponseEntity<Void> sellProduct(@Valid @RequestBody SaleRequest saleRequest) {
        salesService.sellProduct(saleRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}/sales")
    @Operation(summary = "Get sales by client ID", description = "Retrieve sales records for a specific client")
    public List<SaleResponse> getSalesByClientId(@PathVariable UUID id) {
        return salesService.getSalesByClientId(id);
    }

    @GetMapping("/most-sold-products")
    @Operation(summary = "Get most sold products", description = "Retrieve the top 5 most sold products")
    public List<MostSoldProductResponse> getMostSoldProducts() {
        return salesService.getMostSoldProducts();
    }


}
