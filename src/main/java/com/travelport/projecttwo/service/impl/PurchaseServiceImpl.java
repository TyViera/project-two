package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.jpa.PurchaseDetailJpaRepository;
import com.travelport.projecttwo.jpa.PurchaseJpaRespository;
import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.model.PurchaseDetail;
import com.travelport.projecttwo.model.dto.Request.PurchaseRequest;
import com.travelport.projecttwo.service.ProductService;
import com.travelport.projecttwo.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private final PurchaseJpaRespository purchaseDao;
    @Autowired
    private final PurchaseDetailJpaRepository purchaseDetailDao;
    public PurchaseServiceImpl(PurchaseJpaRespository purchaseDao, PurchaseDetailJpaRepository purchaseDetailDao) {
        this.purchaseDao = purchaseDao;
        this.purchaseDetailDao = purchaseDetailDao;
    }
    @Autowired
    private ProductService productService;

    @Override
    public void createPurchase(PurchaseRequest purchaseRequest) {
        String supplier = purchaseRequest.getSupplier();
        Map<UUID, Integer> products = purchaseRequest.getProducts();

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setProducts(new ArrayList<>());

        purchaseDao.save(purchase);

        for (var entry : products.entrySet()) {
            Product product = productService.getProductById(entry.getKey()).get();
            Integer quantity = entry.getValue();

            product.setStock(product.getStock() + quantity);
            productService.updateProduct(entry.getKey(), product);

            PurchaseDetail purchaseDetail = new PurchaseDetail();
            purchaseDetail.setProductId(product);
            purchaseDetail.setQuantity(quantity);
            purchaseDetail.setPurchaseId(purchase);

            purchaseDetailDao.save(purchaseDetail);
        }
    }
}
