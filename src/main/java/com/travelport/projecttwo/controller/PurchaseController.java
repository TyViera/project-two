package com.travelport.projecttwo.controller;


import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.PurchaseRequest;
import com.travelport.projecttwo.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Void> purchaseProduct(@RequestBody PurchaseRequest purchaseRequest) {
        Optional<Product> productOptional = productRepository.findById(purchaseRequest.getProduct().getId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setQuantity(product.getQuantity() + purchaseRequest.getQuantity());
            productRepository.save(product);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
