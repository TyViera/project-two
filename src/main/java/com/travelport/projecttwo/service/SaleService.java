package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleService {

  @Autowired
  private SaleRepository saleRepository;

  @Autowired
  private ProductRepository productRepository;

  @Transactional
  public boolean createSale(@Valid Sale sale) {
    Product product = productRepository.findById(sale.getProduct().getId())
        .orElseThrow(() -> new IllegalArgumentException("Product not found"));

    if (product.getStock() < sale.getQuantity()) {
      return false;
    }

    product.setStock(product.getStock() - sale.getQuantity());
    productRepository.save(product);

    saleRepository.save(sale);
    return true;
  }

  public List<Object[]> getMostSoldProducts() {
    return saleRepository.findMostSoldProducts();
  }
}
