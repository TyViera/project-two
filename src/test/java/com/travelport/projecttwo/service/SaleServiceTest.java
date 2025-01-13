package com.travelport.projecttwo.service;

import com.travelport.projecttwo.dto.SaleRequest;
import com.travelport.projecttwo.entity.Client;
import com.travelport.projecttwo.entity.Product;
import com.travelport.projecttwo.entity.Sale;
import com.travelport.projecttwo.entity.SaleItem;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private SaleService saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sellProductTest() {
        SaleRequest request = new SaleRequest();
        request.setClientId("1");
        request.setProductId("1");
        request.setQuantity(5);

        Client client = new Client();
        client.setId("1");

        Product product = new Product();
        product.setId("1");
        product.setStock(10);

        when(clientRepository.findById(request.getClientId())).thenReturn(Optional.of(client));
        when(productRepository.findById(request.getProductId())).thenReturn(Optional.of(product));

        saleService.sellProduct(request);

        assertEquals(5, product.getStock());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void sellProductInsufficientStockTest() {
        SaleRequest request = new SaleRequest();
        request.setClientId("1");
        request.setProductId("1");
        request.setQuantity(15);

        Product product = new Product();
        product.setId("1");
        product.setStock(10);

        when(productRepository.findById(request.getProductId())).thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class, () -> saleService.sellProduct(request));
    }

    @Test
    void sellProductClientNotFoundTest() {
        SaleRequest request = new SaleRequest();
        request.setClientId("1");

        when(clientRepository.findById(request.getClientId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> saleService.sellProduct(request));
    }
}
