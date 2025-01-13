package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.controller.model.SaleRequest;
import com.travelport.projecttwo.dto.ProductDetails;
import com.travelport.projecttwo.dto.ProductIdName;
import com.travelport.projecttwo.exception.ClientNotFoundException;
import com.travelport.projecttwo.exception.NotEnoughStockException;
import com.travelport.projecttwo.exception.ProductNotFoundException;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.SaleDetailRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import com.travelport.projecttwo.repository.entity.SaleDetailEntity;
import com.travelport.projecttwo.repository.entity.SaleEntity;
import com.travelport.projecttwo.service.SaleService;
import com.travelport.projecttwo.service.model.PastSaleResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

  private final ClientRepository clientRepository;
  private final ProductRepository productRepository;
  private final SaleRepository saleRepository;
  private final SaleDetailRepository saleDetailRepository;

  public SaleServiceImpl(ClientRepository clientRepository, ProductRepository productRepository, SaleRepository saleRepository, SaleDetailRepository saleDetailRepository) {
    this.clientRepository = clientRepository;
    this.productRepository = productRepository;
    this.saleRepository = saleRepository;
    this.saleDetailRepository = saleDetailRepository;
  }

  @Override
  @Transactional
  public void createSale (SaleRequest sale) throws ClientNotFoundException, ProductNotFoundException, NotEnoughStockException {
    var client = sale.getClient();
    var products = sale.getProducts();

    var isClientFound = clientRepository.findById(client.getId());
    if (isClientFound.isEmpty()) throw new ClientNotFoundException(client.getId());

    var saleClient = isClientFound.get();
    var newSale = new SaleEntity(saleClient);
    var createdSale = saleRepository.save(newSale);

    products.forEach(product -> {
      var isProductFound = productRepository.findById(product.getId());
      if (isProductFound.isEmpty()) throw new ProductNotFoundException(product.getId());

      var foundProduct = isProductFound.get();

      var newStock = foundProduct.getStock() - product.getQuantity();
      if (newStock < 0) throw new NotEnoughStockException(product.getId());

      foundProduct.setStock(newStock);
      productRepository.save(foundProduct);

      var saleDetail = new SaleDetailEntity(createdSale, foundProduct, product.getQuantity());
      saleDetailRepository.save(saleDetail);
    });
  }

  @Override
  public List<PastSaleResponse> getSalesByClientId (String clientId) throws ClientNotFoundException {
    var isClientFound = clientRepository.findById(clientId);
    if (isClientFound.isEmpty()) throw new ClientNotFoundException(clientId);

    var pastSalesResponse = new ArrayList<PastSaleResponse>();
    var sales = saleRepository.findByClientId(clientId);


    sales.forEach(sale -> {
      var saleDetails = saleDetailRepository.findBySaleId(sale.getId());
      var products = new ArrayList<ProductDetails>();

      saleDetails.forEach(saleDetail -> {
        var saleProduct = saleDetail.getProduct();

        var product = new ProductIdName(saleProduct.getId(), saleProduct.getName());
        var productDetails = new ProductDetails(product, saleDetail.getQuantity());
        products.add(productDetails);
      });

      var saleResponse = new PastSaleResponse(sale.getId(), products);
      pastSalesResponse.add(saleResponse);
    });

    return pastSalesResponse;
  }

  @Override
  public List<ProductDetails> getMostSoldProducts () {
    var productsReport = new ArrayList<ProductDetails>();

    var mostSoldProducts = saleDetailRepository.findMostSoldProducts();
    mostSoldProducts.forEach(product -> {
      var timesSold = saleDetailRepository.getTimesSoldById(product.getId());

      var productDetails = new ProductIdName(product.getId(), product.getName());
      var productReport = new ProductDetails(productDetails, timesSold);
      productsReport.add(productReport);
    });

    return productsReport;
  }

}
