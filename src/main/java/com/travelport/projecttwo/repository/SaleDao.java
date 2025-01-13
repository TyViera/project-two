package com.travelport.projecttwo.repository;


import com.travelport.projecttwo.entities.SaleEntity;
import com.travelport.projecttwo.entities.SaleProductEntity;

public interface SaleDao {
    void addSale(SaleEntity saleEntity);

    void addSaleProduct(SaleProductEntity saleProductEntity);
}
