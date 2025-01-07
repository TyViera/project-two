package com.travelport.projecttwo.controllers.mappings;

import com.travelport.projecttwo.controllers.dtos.ProductRequestDto;
import com.travelport.projecttwo.controllers.dtos.ProductResponseDto;
import com.travelport.projecttwo.services.domainModels.ProductDomain;

public class ProductMappings {

    public static ProductDomain toDomain(ProductRequestDto productRequest) {
        var productDomain = new ProductDomain();
        productDomain.setCode(productRequest.getCode());
        productDomain.setName(productRequest.getName());
        return productDomain;
    }

    public static ProductResponseDto toDto(ProductDomain productDomain) {
        var productResponse = new ProductResponseDto();
        productResponse.setId(productDomain.getId());
        productResponse.setCode(productDomain.getCode());
        productResponse.setName(productDomain.getName());

        return productResponse;
    }
}
