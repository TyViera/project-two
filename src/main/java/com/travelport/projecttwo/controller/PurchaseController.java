package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.request.PurchaseInput;
import com.travelport.projecttwo.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
@Tag(name = "purchases", description = "The Purchases API")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a purchase",
            description = "Renew stock for a product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Purchase created successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    )
            }
    )
    public ResponseEntity<Void> purchaseProduct(@Valid @RequestBody PurchaseInput purchaseInput) {
        purchaseService.purchaseProduct(purchaseInput);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
