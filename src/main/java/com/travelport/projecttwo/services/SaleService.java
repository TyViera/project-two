package com.travelport.projecttwo.services;

import java.util.List;
import java.util.UUID;

import com.travelport.projecttwo.entities.ProductSale;
import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.requests.ProductSaleRequest; // Import the ProductSaleRequest class

public interface SaleService {

  void sellProduct(UUID clientId, List<ProductSaleRequest> productSaleRequests);
  List<Sale> getPastSales(UUID clientId);
  List<ProductSale> getTop5MostSoldProducts();
  boolean hasSales(UUID clientId);
}
