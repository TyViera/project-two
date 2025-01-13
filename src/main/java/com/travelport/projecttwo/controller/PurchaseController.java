package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.controller.model.PurchaseRequest;
import com.travelport.projecttwo.dto.ErrorResponse;
import com.travelport.projecttwo.exception.ProductNotFoundException;
import com.travelport.projecttwo.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
@Tag(name = "purchases", description = "Operations to purchase products (renew stock)")
public class PurchaseController {

  private final PurchaseService purchaseService;

  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  @Operation(
      operationId = "purchaseProduct",
      summary = "Purchase a product (renew stock)",
      description = "This operation must be protected with basic auth",
      tags = { "purchases" },
      responses = {
          @ApiResponse(responseCode = "201", description = "successful operation")
      },
      security = {
          @SecurityRequirement(name = "basicAuth")
      }
  )
  @PostMapping
  public ResponseEntity<?> renewStock (@RequestBody PurchaseRequest purchase) {
    try {
      purchaseService.renewStock(purchase);
      return ResponseEntity.status(201).build();
    } catch (ProductNotFoundException e) {
      return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage() + ". Operation cancelled"));
    }

  }

}
