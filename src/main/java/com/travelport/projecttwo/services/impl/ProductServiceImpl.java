package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.exceptions.ProductHasSalesException;
import com.travelport.projecttwo.repository.IProductRepository;
import com.travelport.projecttwo.repository.ISalesDetRepository;
import com.travelport.projecttwo.repository.entities.ProductEntity;
import com.travelport.projecttwo.services.IProductService;
import com.travelport.projecttwo.services.domainModels.ProductDomain;
import com.travelport.projecttwo.services.mappings.ProductMappings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ISalesDetRepository salesDetRepository;

    public ProductServiceImpl(IProductRepository productRepository, ISalesDetRepository salesDetRepository) {
        this.productRepository = productRepository;
        this.salesDetRepository = salesDetRepository;
    }

    @Override
    public List<ProductDomain> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductMappings::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDomain> getProductById(String id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        return productEntity.map(ProductMappings::toDomain);
    }

    @Override
    public ProductDomain createProduct(ProductDomain productDomain) {
        var productExists = productRepository.existsByCode(productDomain.getCode());

        if (productExists) {
            throw new IllegalArgumentException("Product already exists");
        }

        productDomain.setId(UUID.randomUUID().toString());
        productDomain.setStock(0);
        var productEntity = productRepository.save(ProductMappings.toEntity(productDomain));

        return ProductMappings.toDomain(productEntity);
    }

    @Override
    public ProductDomain updateProduct(String id, ProductDomain productDomain) {
        var productEntity = productRepository.findById(id);
        if (productEntity.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        productEntity.get().setName(productDomain.getName());
        productEntity.get().setCode(productDomain.getCode());

        productRepository.save(productEntity.get());

        return ProductMappings.toDomain(productEntity.get());
    }

    @Override
    public void deleteProduct(String id) {
        var productEntity = productRepository.findById(id);
        if (productEntity.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        var productHasSales = salesDetRepository.existsByIdProductId(productEntity.get().getId());

        if (productHasSales) {
            throw new ProductHasSalesException("Product has sales");
        }

        productRepository.delete(productEntity.get());
    }
}
