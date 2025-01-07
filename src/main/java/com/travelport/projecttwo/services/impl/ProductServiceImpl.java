package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.repository.IProductRepository;
import com.travelport.projecttwo.services.IProductService;
import com.travelport.projecttwo.services.domainModels.ProductDomain;
import com.travelport.projecttwo.services.mappings.ProductMappings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    public ProductServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDomain> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductMappings::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDomain createProduct(ProductDomain productDomain) {
        productDomain.setId(UUID.randomUUID().toString());
        productDomain.setStock(0);
        var productEntity = productRepository.save(ProductMappings.toEntity(productDomain));

        return ProductMappings.toDomain(productEntity);
    }
}
