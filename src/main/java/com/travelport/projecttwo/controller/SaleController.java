package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.SaleRequest;
import com.travelport.projecttwo.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sellProduct(@RequestBody SaleRequest request) {
        saleService.sellProduct(request);
    }
}
