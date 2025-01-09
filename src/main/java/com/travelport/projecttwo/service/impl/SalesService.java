package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.entities.*;
import com.travelport.projecttwo.jpa.ProductRepository;
import com.travelport.projecttwo.jpa.SalesRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<SaleResponse> getSalesByClientId(UUID clientId) {
        List<SalesRecord> salesRecords = salesRecordRepository.findByClientId(clientId);

        return salesRecords.stream().map(salesRecord -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setId(salesRecord.getId());

            Product product = productRepository.findById(salesRecord.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ProductSaleResponse productSaleResponse = new ProductSaleResponse();
            productSaleResponse.setProductId(product.getId());
            productSaleResponse.setProductName(product.getName());
            productSaleResponse.setQuantity(salesRecord.getQuantitySold());

            saleResponse.setProducts(List.of(productSaleResponse));
            return saleResponse;
        }).collect(Collectors.toList());
    }

    public List<MostSoldProductResponse> getMostSoldProducts() {
        List<SalesRecord> salesRecords = salesRecordRepository.findTop5ByOrderByQuantitySoldDesc();

        return salesRecords.stream().map(salesRecord -> {
            Product product = productRepository.findById(salesRecord.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            MostSoldProductResponse response = new MostSoldProductResponse();
            response.setProductId(product.getId());
            response.setProductName(product.getName());
            response.setQuantity(salesRecord.getQuantitySold());

            return response;
        }).collect(Collectors.toList());
    }
}
