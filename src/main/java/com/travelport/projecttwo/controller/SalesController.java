package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.ProductReportResponse;
import com.travelport.projecttwo.dto.SaleRequest;
import com.travelport.projecttwo.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sales")
@RestController
@RequestMapping("/sales")
public class SalesController {
    private final SaleService saleService;

    public SalesController (SaleService saleService) {
        this.saleService = saleService;
    }

    @Operation(summary = "Sell product", description = "Processes the sale of a product",  security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale successfully created"),
            @ApiResponse(responseCode = "404", description = "Product not found or other errors")
    })
    @PostMapping
    public ResponseEntity<String> sellProduct(@RequestBody SaleRequest saleRequest) {
        try{
            saleService.addSale(saleRequest);
            return new ResponseEntity<>("Operation Successful", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get most sold products", description = "Retrieves a list of the most sold products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved most sold products", content = @Content(
                    schema = @Schema(type = "array", implementation = ProductReportResponse.class))),
            @ApiResponse(responseCode = "404", description = "Error retrieving product data", content = @Content())
    })
    @GetMapping("/most-sold-products")
    public  ResponseEntity<Object> getMostSoldProducts(){
        try{
            List<ProductReportResponse> products= saleService.getMostSoldProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
