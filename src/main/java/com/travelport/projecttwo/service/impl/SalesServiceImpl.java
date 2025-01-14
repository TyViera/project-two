package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.jpa.SalesDetailJpaRepository;
import com.travelport.projecttwo.jpa.SalesJpaRepository;
import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.model.SaleDetail;
import com.travelport.projecttwo.model.dto.MostSoldProduct;
import com.travelport.projecttwo.model.dto.Request.SalesRequest;
import com.travelport.projecttwo.service.ClientService;
import com.travelport.projecttwo.service.ProductService;
import com.travelport.projecttwo.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private final SalesJpaRepository salesDao;
    @Autowired
    private SalesDetailJpaRepository saleDetailDao;
    public SalesServiceImpl(SalesJpaRepository salesDao, SalesDetailJpaRepository saleDetailDao) {
        this.salesDao = salesDao;
        this.saleDetailDao = saleDetailDao;
    }
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;


    @Override
    public void createSale(SalesRequest salesRequest) {
        Client client = clientService.getClientByID(salesRequest.getClientId()).get();
        Map<UUID, Integer> products = salesRequest.getProducts();

        Sale sale = new Sale();
        sale.setClientId(client);
        sale.setProducts(new ArrayList<>());
        salesDao.save(sale);

        for (var entry : products.entrySet()) {
            Product product = productService.getProductById(entry.getKey()).get();
            Integer quantity = entry.getValue();

            product.setStock(product.getStock() - quantity);
            productService.updateProduct(entry.getKey(), product);

            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setProductId(product);
            saleDetail.setQuantity(quantity);
            saleDetail.setSaleId(sale);

            saleDetailDao.save(saleDetail);
        }
    }

    @Override
    public List<Map<String, Object>> getPastSalesByClientId(UUID clientId) {
        List<Object[]> results = salesDao.findLastSalesByClientId(clientId);
        Map<UUID, Map<String, Object>> salesMap = new LinkedHashMap<>();

        for (Object[] row : results) {
            UUID saleId = (UUID) row[0];
            UUID productId = (UUID) row[1];
            Integer quantity = (Integer) row[2];
            String productName = (String) row[3];

            salesMap.putIfAbsent(saleId, new HashMap<>() {{
                put("id", saleId);
                put("products", new ArrayList<Map<String, Object>>());
            }});

            List<Map<String, Object>> products = (List<Map<String, Object>>) salesMap.get(saleId).get("products");
            products.add(new HashMap<>() {{
                put("product", new HashMap<String, Object>() {{
                    put("id", productId);
                    put("name", productName);
                }});
                put("quantity", quantity);
            }});
        }

        return new ArrayList<>(salesMap.values());
    }

    @Override
    public List<MostSoldProduct> getTop5Products() {
        List<Object[]> results = salesDao.findTop5MostSoldProducts();

        return results.stream().map(row -> {
            MostSoldProduct mostSoldProduct = new MostSoldProduct();
            mostSoldProduct.setProductId((UUID) row[0]);
            mostSoldProduct.setProductName((String) row[1]);
            mostSoldProduct.setQuantity(((Number) row[2]).intValue());
            return mostSoldProduct;
        }).collect(Collectors.toList());
    }
}
