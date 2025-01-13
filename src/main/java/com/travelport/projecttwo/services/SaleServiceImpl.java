package com.travelport.projecttwo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductSale;
import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.repositories.ClientRepository;
import com.travelport.projecttwo.repositories.ProductRepository;
import com.travelport.projecttwo.repositories.ProductSaleRepository;
import com.travelport.projecttwo.repositories.ProductStockRepository;
import com.travelport.projecttwo.repositories.SaleRepository;
import com.travelport.projecttwo.requests.ProductSaleRequest;

@Service
public class SaleServiceImpl implements SaleService {

  @Autowired
  private SaleRepository saleRepository;

  @Autowired
  private ProductSaleRepository productSaleRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductStockRepository productStockRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public void sellProduct(UUID clientId, List<ProductSaleRequest> productSaleRequests) {
    System.out.println("Received clientId: " + clientId);
    productSaleRequests.forEach(request ->
        System.out.println("Product ID: " + request.getId() + ", Quantity: " + request.getQuantity())
    );

    Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new RuntimeException("Client not found"));

    Sale sale = new Sale(client, new ArrayList<>());

    for (ProductSaleRequest productSaleRequest : productSaleRequests) {
      Product product = productRepository.findById(productSaleRequest.getId())
          .orElseThrow(() -> new RuntimeException("Product not found"));

      ProductStock productStock = productStockRepository.findByProductId(productSaleRequest.getId())
          .orElseThrow(() -> new RuntimeException("Product stock not found"));

      if (productStock.getQuantity() < productSaleRequest.getQuantity()) {
        throw new RuntimeException("Not enough stock available for product: " + product.getName());
      }

      ProductSale productSale = new ProductSale();
      productSale.setProduct(product);
      productSale.setSale(sale);
      productSale.setQuantity(productSaleRequest.getQuantity());
      sale.getProducts().add(productSale);

      productStock.setQuantity(productStock.getQuantity() - productSaleRequest.getQuantity());
      productStockRepository.save(productStock);
    }

    saleRepository.save(sale);
  }

  @Override
  public List<Sale> getPastSales(UUID clientId) {
    return saleRepository.findByClientId(clientId);
  }

  @Override
  public List<ProductSale> getTop5MostSoldProducts() {
    return productSaleRepository.findTop5();
  }

  @Override
  public boolean hasSales(UUID clientId) {
    return saleRepository.existsByClientId(clientId);
  }
}
