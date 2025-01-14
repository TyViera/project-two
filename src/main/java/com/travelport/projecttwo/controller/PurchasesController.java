package com.travelport.projecttwo.controller;


import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.model.dto.Request.PurchaseRequest;
import com.travelport.projecttwo.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    private final PurchaseService purchaseService;
    public PurchasesController(PurchaseService purchaseService) { this.purchaseService = purchaseService; }

    @PostMapping
    @Operation (
            tags = "purchases",
            description = "Create purchase",
            operationId = "createPurchase",
            responses = {
                    @ApiResponse (
                            responseCode = "201",
                            description = "Purchase created",
                            content = {
                                    @Content (
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Purchase.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<List<Purchase>> createPurchase(@RequestBody PurchaseRequest purchaseRequest) {
        purchaseService.createPurchase(purchaseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
