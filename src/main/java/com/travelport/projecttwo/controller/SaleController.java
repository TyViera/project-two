package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.controller.model.SaleRequest;
import com.travelport.projecttwo.dto.ErrorResponse;
import com.travelport.projecttwo.dto.ProductDetails;
import com.travelport.projecttwo.exception.NotEnoughStockException;
import com.travelport.projecttwo.exception.SaleException;
import com.travelport.projecttwo.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
@Tag(name = "sales", description = "Operations to sell products")
public class SaleController {

  private final SaleService saleService;

  public SaleController(SaleService saleService) {
    this.saleService = saleService;
  }

  @Operation(
      operationId = "sellProduct",
      summary = "Sell a product",
      description = "This operation must be protected with basic auth",
      tags = { "sales" },
      responses = {
          @ApiResponse(responseCode = "201", description = "successful operation")
      },
      security = {
          @SecurityRequirement(name = "basicAuth")
      }
  )
  @PostMapping
  public ResponseEntity<?> createSale (@RequestBody SaleRequest sale) {
    try {
      saleService.createSale(sale);
      return ResponseEntity.status(201).build();
    } catch (NotEnoughStockException e) {
      return ResponseEntity.status(409).body(new ErrorResponse(e.getMessage() + ". Operation cancelled"));
    } catch (SaleException e) {
      return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage() + ". Operation cancelled"));
    }
  }

  @Operation(
      operationId = "getMostSoldProducts",
      summary = "See income report",
      description = "See the 5 most sold products.  This operation must be protected with basic auth. It must include the 5 most sold products, ordered desc by quantity. ",
      tags = { "sales" },
      responses = {
          @ApiResponse(responseCode = "200", description = "list of products", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductDetails.class)))
          })
      },
      security = {
          @SecurityRequirement(name = "basicAuth")
      }
  )
  @GetMapping("/most-sold-products")
  public ResponseEntity<?> getMostSoldProducts() {
    return ResponseEntity.ok(saleService.getMostSoldProducts());
  }
}
