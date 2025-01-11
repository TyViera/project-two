package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.ProductReportResponse;
import com.travelport.projecttwo.dto.SaleRequest;
import com.travelport.projecttwo.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//TODO: maybe this could be only @Controller
@RequestMapping("/sales")
public class SalesController {
    private final SaleService saleService;

    public SalesController (SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<String> sellProduct(@RequestBody SaleRequest saleRequest) {
        //TODO: catch different exceptions
        try{
            saleService.addSale(saleRequest);
            return new ResponseEntity<>("Operation Successful", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

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
