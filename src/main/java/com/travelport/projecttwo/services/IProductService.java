package com.travelport.projecttwo.services;

import com.travelport.projecttwo.services.domainModels.ProductDomain;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<ProductDomain> getProducts();

    Optional<ProductDomain> getProductById(String id);

    ProductDomain createProduct(ProductDomain productDomain);

    ProductDomain updateProduct(String id, ProductDomain productDomain);
}
