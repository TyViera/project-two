package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.SaleRequest;
import com.travelport.projecttwo.entities.SalesRecord;
import com.travelport.projecttwo.jpa.ProductRepository;
import com.travelport.projecttwo.jpa.SalesRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SalesService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesRecordRepository salesRecordRepository;

    public void sellProduct(SaleRequest saleRequest) {
        Product product = productRepository.findById(saleRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < saleRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setQuantity(product.getQuantity() - saleRequest.getQuantity());
        productRepository.save(product);

        SalesRecord salesRecord = new SalesRecord();
        salesRecord.setProductId(saleRequest.getProductId());
        salesRecord.setClientId(saleRequest.getClientId());
        salesRecord.setQuantitySold(saleRequest.getQuantity());

        salesRecordRepository.save(salesRecord);
    }
}
