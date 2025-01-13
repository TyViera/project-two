package com.travelport.projecttwo.service;

import com.travelport.projecttwo.controller.model.ProductRequest;
import com.travelport.projecttwo.exception.DeletingProductException;
import com.travelport.projecttwo.exception.DuplicatedCodeException;
import com.travelport.projecttwo.repository.entity.ProductEntity;
import com.travelport.projecttwo.service.model.ProductResponse;
import com.travelport.projecttwo.service.model.StockResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

  public List<ProductResponse> getAll ();

  public Optional<ProductResponse> save (ProductRequest inputProduct) throws DuplicatedCodeException;

  public Optional<ProductResponse> getById (String id);

  public Optional<ProductResponse> updateById (String id, ProductRequest inputProduct) throws DuplicatedCodeException;

  public boolean deleteById (String id) throws DeletingProductException;

  public Optional<StockResponse> getStockById (String id);

}
