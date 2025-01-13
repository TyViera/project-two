package com.travelport.projecttwo.service;

import com.travelport.projecttwo.controller.model.SaleRequest;
import com.travelport.projecttwo.dto.ProductDetails;
import com.travelport.projecttwo.exception.ClientNotFoundException;
import com.travelport.projecttwo.exception.NotEnoughStockException;
import com.travelport.projecttwo.exception.ProductNotFoundException;
import com.travelport.projecttwo.service.model.PastSaleResponse;

import java.util.List;

public interface SaleService {

  public void createSale (SaleRequest sale) throws ClientNotFoundException, ProductNotFoundException, NotEnoughStockException;

  public List<PastSaleResponse> getSalesByClientId (String clientId) throws ClientNotFoundException;

  public List<ProductDetails> getMostSoldProducts ();

}
