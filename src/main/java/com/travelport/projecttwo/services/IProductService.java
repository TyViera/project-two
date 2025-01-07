package com.travelport.projecttwo.services;

import com.travelport.projecttwo.services.domainModels.ProductDomain;

public interface IProductService {

    ProductDomain createProduct(ProductDomain productDomain);
}
