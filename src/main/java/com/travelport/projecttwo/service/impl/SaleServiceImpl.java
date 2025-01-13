package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.dto.ProductReportResponse;
import com.travelport.projecttwo.dto.SaleRequest;
import com.travelport.projecttwo.dto.SaleResponse;
import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.entities.SaleDetail;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.SaleDetailRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import com.travelport.projecttwo.service.SaleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final ClientRepository clientRepository;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SaleDetailRepository saleDetailRepository;

    @Autowired //TODO: check if this is always necessary
    public SaleServiceImpl(ClientRepository clientRepository, SaleRepository saleRepository, ProductRepository productRepository, SaleDetailRepository saleDetailRepository) {
        this.clientRepository = clientRepository;
        this.saleRepository=saleRepository;
        this.productRepository=productRepository;
        this.saleDetailRepository=saleDetailRepository;
    }

    @Override
    public List<ProductReportResponse> getMostSoldProducts() {
        //TODO: maybe change this to ProductService(? and refactor
        List<Object[]> results = productRepository.getMostSoldProducts();
        List<ProductReportResponse> responseList = results.stream()
                .map(result -> new ProductReportResponse(
                        (String) result[0],
                        (String) result[1],
                        (BigDecimal) result[2]
                ))
                .collect(Collectors.toList());
        return responseList;
    }

    @Override
    public void addSale(SaleRequest saleRequest) {
        SaleRequest.ClientRequest clientRequested= saleRequest.getClient();
        String clientId = clientRequested.getId();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));

        Sale sale= new Sale(UUID.randomUUID().toString(), client);
        List<SaleRequest.ProductRequest> requestedProducts = saleRequest.getProducts();
        saleRepository.save(sale);

        for(SaleRequest.ProductRequest p: requestedProducts ){
            Product fProduct = productRepository.findById(p.getId())
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));

            if(p.getQuantity()>fProduct.getStock() && fProduct.getStock()>0) throw new IllegalArgumentException("Requested stock not available");

            fProduct.setStock(fProduct.getStock()-p.getQuantity());
            productRepository.save(fProduct);

            SaleDetail saleDetail = new SaleDetail(sale.getId(), p.getId(), p.getQuantity(), fProduct, sale );
            saleDetailRepository.save(saleDetail);
        }
    }
    @Override
    public List<SaleResponse> getSalesByClientId(String id) {
        List<SaleResponse> sales = new ArrayList<>();
        List<String> salesId = saleRepository.getSalesIdByClientId(id);
        if (salesId.isEmpty()) {
            throw new NoSuchElementException("Client has no sales");
        }

        for (String saleId : salesId) {
            List<Product> products = new ArrayList<>();
            List<SaleDetail> saleDetails = saleDetailRepository.findBySaleId(saleId);
            if (saleDetails.isEmpty()) {
                throw new NoSuchElementException("No sale details found for sale ID: " + saleId);
            }

            for (SaleDetail saleDetail : saleDetails) {
                Product product = productRepository.findById(saleDetail.getProductId())
                        .orElseThrow(() -> new NoSuchElementException(
                        "Product not found for product ID: " + saleDetail.getProductId()));
                if (product == null) {
                    throw new NoSuchElementException("Product not found for product ID: " + saleDetail.getProductId());
                }
                products.add(product);
            }

            SaleResponse sale = new SaleResponse(saleId, saleDetails, products);
            sales.add(sale);
        }
        return sales;
    }


}
