package com.travelport.projecttwo.services.mappings;

import com.travelport.projecttwo.repository.entities.ProductEntity;
import com.travelport.projecttwo.services.domainModels.ProductDomain;

public class ProductMappings {

    public static ProductEntity toEntity(ProductDomain productDomain) {
        var productEntity = new ProductEntity();
        productEntity.setId(productDomain.getId());
        productEntity.setCode(productDomain.getCode());
        productEntity.setName(productDomain.getName());
        productEntity.setStock(productDomain.getStock());

        return productEntity;
    }

    public static ProductDomain toDomain(ProductEntity productEntity) {
        var productDomain = new ProductDomain();
        productDomain.setId(productEntity.getId());
        productDomain.setCode(productEntity.getCode());
        productDomain.setName(productEntity.getName());
        productDomain.setStock(productEntity.getStock());

        return productDomain;
    }
}
