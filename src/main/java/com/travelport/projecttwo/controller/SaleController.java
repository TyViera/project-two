package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.findAll();
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<List<Sale>> getSalesByClientId(@PathVariable String clientId) {
        List<Sale> sales = saleService.findByClientId(clientId);
        if (sales.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sales);
    }

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody Sale sale) {
        try {
            Sale savedSale = saleService.save(sale);
            return ResponseEntity.status(201).body(savedSale);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
