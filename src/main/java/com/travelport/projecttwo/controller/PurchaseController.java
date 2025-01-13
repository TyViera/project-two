package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.PurchaseRequest;
import com.travelport.projecttwo.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Purchases")
@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "Purchase product", description = "Handles the purchase of a product and updates the stock.",  security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully purchased"),
            @ApiResponse(responseCode = "406", description = "Purchase was not made due to invalid data or other issues")
    })
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
