package com.travelport.projecttwo.services;

import com.travelport.projecttwo.controllers.dtos.sale.MostSoldProductsDto;
import com.travelport.projecttwo.controllers.dtos.sale.SaleRequestDto;

import java.util.List;

public interface ISaleService {

    void createSale(SaleRequestDto saleRequest);

    List<MostSoldProductsDto> getMostSoldProducts();
}
