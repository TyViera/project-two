package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.model.dto.MostSoldProduct;
import com.travelport.projecttwo.model.dto.Request.SalesRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface SalesService {
    void createSale(SalesRequest salesRequest);
    List<Map<String, Object>> getPastSalesByClientId(UUID id);
    List<MostSoldProduct> getTop5Products();
}
