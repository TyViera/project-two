package com.travelport.projecttwo.controller;


import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.model.dto.MostSoldProduct;
import com.travelport.projecttwo.model.dto.Request.SalesRequest;
import com.travelport.projecttwo.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;
    public SalesController(SalesService salesService) { this.salesService = salesService; }

    @PostMapping
    public ResponseEntity<Object> createSale(@RequestBody SalesRequest salesRequest) {
        salesService.createSale(salesRequest);
        System.out.println(salesRequest.getClientId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/clients/{id}/sales")
    @Operation (
            tags = "sales",
            description = "Get past sales of a client",
            operationId = "getPastSalesByClientId",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Past sales for this client have been found",
                            content = {
                                    @Content (
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Sale.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<List<Map<String, Object>>> getPastSalesByClientId(@PathVariable UUID id) {
        List<Map<String, Object>> sales = salesService.getPastSalesByClientId(id);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/clients/most-sold-products")
    @Operation (
            tags = "sales",
            description = "Get 5 most sold products",
            operationId = "getPastSalesByClientId",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Sales for this client have been found",
                            content = {
                                    @Content (
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Sale.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<List<MostSoldProduct>> getTop5Products() {
        List<MostSoldProduct> mostSoldProducts = salesService.getTop5Products();

        if (mostSoldProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mostSoldProducts);
    }
}
