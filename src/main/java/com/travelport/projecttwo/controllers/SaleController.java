package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.controllers.dtos.sale.SaleRequestDto;
import com.travelport.projecttwo.services.ISaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final ISaleService saleService;

    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity createSale(@Validated @RequestBody SaleRequestDto saleRequest) {
        try {
            saleService.createSale(saleRequest);
            URI location = URI.create("/sales");
            return ResponseEntity.created(location).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
