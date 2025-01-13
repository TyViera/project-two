package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.controller.model.ProductRequest;
import com.travelport.projecttwo.exception.DeletingProductException;
import com.travelport.projecttwo.exception.DuplicatedCodeException;
import com.travelport.projecttwo.repository.SaleDetailRepository;
import com.travelport.projecttwo.repository.entity.ProductEntity;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.service.ProductService;
import com.travelport.projecttwo.service.model.ProductResponse;
import com.travelport.projecttwo.service.model.StockResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final SaleDetailRepository saleDetailRepository;

  public ProductServiceImpl(ProductRepository productRepository,SaleDetailRepository saleDetailRepository) {
    this.productRepository = productRepository;
    this.saleDetailRepository = saleDetailRepository;
  }

  @Override
  public List<ProductResponse> getAll() {
    var products = productRepository.findAllByOrderByCode();
    var responseList = new ArrayList<ProductResponse>();

    products.forEach(product -> {
      var productResponse = new ProductResponse(product);
      responseList.add(productResponse);
    });

    return responseList;
  }

  @Override
  public Optional<ProductResponse> save(ProductRequest inputProduct) throws DuplicatedCodeException {
    var isProductExists = productRepository.existsByCode(inputProduct.getCode());
    if (isProductExists) throw new DuplicatedCodeException("There is already a product with the provided code");

    var newProduct = new ProductEntity(inputProduct);
    var savedProduct = productRepository.save(newProduct);

    var productResponse = new ProductResponse(savedProduct);
    return Optional.of(productResponse);
  }

  @Override
  public Optional<ProductResponse> getById(String id) {
    var foundProduct = productRepository.findById(id);
    if (foundProduct.isEmpty()) return Optional.empty();

    var productResponse = new ProductResponse(foundProduct.get());
    return Optional.of(productResponse);
  }

  @Override
  public Optional<ProductResponse> updateById(String id, ProductRequest inputProduct) throws DuplicatedCodeException {
    var foundProduct = productRepository.findById(id);
    if (foundProduct.isEmpty()) return Optional.empty();

    var updatedProduct = foundProduct.get();
    var isCodeRepeated = productRepository.existsByCode(inputProduct.getCode());
    if (isCodeRepeated && !Objects.equals(inputProduct.getCode(), updatedProduct.getCode()))
      throw new DuplicatedCodeException("There is already a product with the provided code");

    updatedProduct.setName(inputProduct.getName());
    updatedProduct.setCode(inputProduct.getCode());

    var savedProduct = productRepository.save(updatedProduct);

    var productResponse = new ProductResponse(savedProduct);
    return Optional.of(productResponse);
  }

  @Override
  public boolean deleteById(String id) throws DeletingProductException {
    var isProductExists = productRepository.existsById(id);
    if (!isProductExists) return false;

    if (!saleDetailRepository.findByProductId(id).isEmpty())
      throw new DeletingProductException("Product has been sold previously, cannot be deleted");

    productRepository.deleteById(id);
    return true;
  }

  @Override
  public Optional<StockResponse> getStockById(String id) {
    var foundProduct = productRepository.findById(id);
    if (foundProduct.isEmpty()) return Optional.empty();

    var product = foundProduct.get();
    var stockResponse = new StockResponse(product.getStock());
    return Optional.of(stockResponse);
  }

}
