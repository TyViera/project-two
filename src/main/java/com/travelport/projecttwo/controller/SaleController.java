package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.Response.MostSoldProductResponse;
import com.travelport.projecttwo.model.Response.SaleHistoryResponse;
import com.travelport.projecttwo.service.IncomeReportService;
import com.travelport.projecttwo.service.SaleService;
import com.travelport.projecttwo.service.SaleHistoryService;
import com.travelport.projecttwo.model.request.SaleInput;
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

import java.util.List;

@RestController
@RequestMapping("/sales")
@Tag(name = "sales", description = "The Sales API")
public class SaleController {

    private final SaleService saleService;
    private final IncomeReportService incomeReportService;
    private final SaleHistoryService saleHistoryService;

    public SaleController(SaleService saleService, IncomeReportService incomeReportService, SaleHistoryService saleHistoryService) {
        this.saleService = saleService;
        this.incomeReportService = incomeReportService;
        this.saleHistoryService = saleHistoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a sale",
            description = "Sells a product to a client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sale created successfully"),
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
    public ResponseEntity<Void> sellProduct(@Valid @RequestBody SaleInput saleInput) {
        saleService.sellProduct(saleInput);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/most-sold-products")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Get the 5 most sold products",
            description = "Returns the 5 products with the highest sales, ordered by quantity sold.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of most sold products")
            }
    )
    public ResponseEntity<List<MostSoldProductResponse>> getMostSoldProducts() {
        List<MostSoldProductResponse> products = incomeReportService.getMostSoldProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/clients/{id}/sales")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "See past sales",
            description = "Returns a list of past sales for a client, including the products and quantities.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved past sales"),
                    @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)
                    ))
            }
    )
    public ResponseEntity<List<SaleHistoryResponse>> getSalesHistoryByClientId(@PathVariable String id) {
        List<SaleHistoryResponse> salesHistory = saleHistoryService.getSalesHistoryByClientId(id);

        if (salesHistory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(salesHistory);
    }
}