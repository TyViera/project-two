package com.travelport.projecttwo.services;

import com.travelport.projecttwo.services.domainModels.ProductDomain;

import java.util.List;

public interface IProductService {

    List<ProductDomain> getProducts();

    ProductDomain createProduct(ProductDomain productDomain);
}
