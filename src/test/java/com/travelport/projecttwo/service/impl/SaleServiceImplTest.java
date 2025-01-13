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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SaleServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SaleDetailRepository saleDetailRepository;

    @InjectMocks
    private SaleServiceImpl saleService;

    private Client client;
    private Product product;
    private SaleRequest saleRequest;
    private Sale sale;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client("clientId", "1231231A", "Sancho Panza", "La mancha 2");
        product = new Product("productId1", "Product 1", "123QWE");
        sale = new Sale("saleId", client);

        SaleRequest.ProductRequest productRequest = new SaleRequest.ProductRequest();
        productRequest.setId("productId1");
        productRequest.setQuantity(2);

        List<SaleRequest.ProductRequest> productRequests = Collections.singletonList(productRequest);
        SaleRequest.ClientRequest clientRequest = new SaleRequest.ClientRequest();
        clientRequest.setId("clientId");

        saleRequest = new SaleRequest();
        saleRequest.setClient(clientRequest);
        saleRequest.setProducts(productRequests);
    }

    @Test
    void getMostSoldProducts() {
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {"productId1", "Product 1", BigDecimal.valueOf(200.00)});
        when(productRepository.getMostSoldProducts()).thenReturn(results);

        List<ProductReportResponse> mostSoldProducts = saleService.getMostSoldProducts();

        assertNotNull(mostSoldProducts);
        assertEquals(1, mostSoldProducts.size());
        assertEquals("productId1", mostSoldProducts.get(0).getProduct().getId());
        assertEquals("Product 1", mostSoldProducts.get(0).getProduct().getName());
        assertEquals(BigDecimal.valueOf(200.00), mostSoldProducts.get(0).getQuantity());
    }

    @Test
    void addSale() {
        when(clientRepository.findById("clientId")).thenReturn(Optional.of(client));
        when(productRepository.findById("productId1")).thenReturn(Optional.of(product));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        when(saleDetailRepository.save(any(SaleDetail.class))).thenReturn(new SaleDetail(sale.getId(), product.getId(), 2, product, sale));

        saleService.addSale(saleRequest);

        verify(clientRepository).findById("clientId");
        verify(productRepository).findById("productId1");
        verify(saleRepository).save(any(Sale.class));
        verify(saleDetailRepository).save(any(SaleDetail.class));
    }

    @Test
    void addSale_productNotFound() {
        when(clientRepository.findById("clientId")).thenReturn(Optional.of(client));
        when(productRepository.findById("productId1")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> saleService.addSale(saleRequest));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void getSalesByClientId() {
        // Mock sale details and product
        SaleDetail saleDetail = new SaleDetail("saleId", "productId1", 2, product, sale);
        List<SaleDetail> saleDetails = Collections.singletonList(saleDetail);

        when(saleRepository.getSalesIdByClientId("clientId")).thenReturn(Collections.singletonList("saleId"));
        when(saleDetailRepository.findBySaleId("saleId")).thenReturn(saleDetails);
        when(productRepository.findById("productId1")).thenReturn(Optional.of(product));

        List<SaleResponse> sales = saleService.getSalesByClientId("clientId");

        assertNotNull(sales);
        assertEquals(1, sales.size());
        assertEquals("saleId", sales.get(0).getId());
        assertEquals(1, sales.get(0).getProducts().size());
        assertEquals("productId1", sales.get(0).getProducts().get(0).getProduct().getId());
        assertEquals("2", sales.get(0).getProducts().get(0).getQuantity());
    }

    @Test
    void getSalesByClientId_noSales() {
        when(saleRepository.getSalesIdByClientId("clientId")).thenReturn(Collections.emptyList());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> saleService.getSalesByClientId("clientId"));
        assertEquals("Client has no sales", exception.getMessage());
    }

    @Test
    void getSalesByClientId_noProductFound() {
        when(saleRepository.getSalesIdByClientId("clientId")).thenReturn(Collections.singletonList("saleId"));
        when(saleDetailRepository.findBySaleId("saleId")).thenReturn(Collections.singletonList(new SaleDetail("saleId", "productId1", 2, product, sale)));
        when(productRepository.findById("productId1")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> saleService.getSalesByClientId("clientId"));
        assertEquals("Product not found for product ID: productId1", exception.getMessage());
    }
}
