package com.travelport.projecttwo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductSale;
import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.repositories.ClientRepository;
import com.travelport.projecttwo.repositories.ProductRepository;
import com.travelport.projecttwo.repositories.ProductSaleRepository;
import com.travelport.projecttwo.repositories.ProductStockRepository;
import com.travelport.projecttwo.repositories.SaleRepository;
import com.travelport.projecttwo.requests.ProductSaleRequest;
import com.travelport.projecttwo.services.SaleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

  @InjectMocks
  private SaleServiceImpl saleService;

  @Mock
  private SaleRepository saleRepository;

  @Mock
  private ProductSaleRepository productSaleRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductStockRepository productStockRepository;

  @Mock
  private ClientRepository clientRepository;

  private UUID clientId;
  private UUID productId;
  private Product product;
  private ProductStock productStock;
  private Client client;
  private ProductSaleRequest productSaleRequest;

  @BeforeEach
  void setUp() {
    clientId = UUID.fromString("12345678-1234-1234-1234-123456789A01");
    productId = UUID.fromString("23456789-2345-2345-2345-234567890B02");

    client = new Client();
    client.setId(clientId);
    client.setName("Test Client");

    product = new Product();
    product.setId(productId);
    product.setName("Test Product");
    product.setCode("TP001");

    productStock = new ProductStock();
    productStock.setProduct(product);
    productStock.setQuantity(100);

    productSaleRequest = new ProductSaleRequest();
    productSaleRequest.setId(productId);
    productSaleRequest.setQuantity(50);
  }

  @Test
  void testSellProduct() {
    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(productStock));

    saleService.sellProduct(clientId, Collections.singletonList(productSaleRequest));

    assertEquals(50, productStock.getQuantity());

    verify(clientRepository, times(1)).findById(clientId);
    verify(productRepository, times(1)).findById(productId);
    verify(productStockRepository, times(1)).findByProductId(productId);
    verify(productStockRepository, times(1)).save(productStock); // stock should be saved with updated quantity
    verify(saleRepository, times(1)).save(any(Sale.class));  // Sale should be saved
  }

  @Test
  void testSellProductNotEnoughStock() {
    productStock.setQuantity(30);

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(productStock));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      saleService.sellProduct(clientId, Collections.singletonList(productSaleRequest));
    });

    assertEquals("Not enough stock available for product: Test Product", exception.getMessage());

    verify(clientRepository, times(1)).findById(clientId);
    verify(productRepository, times(1)).findById(productId);
    verify(productStockRepository, times(1)).findByProductId(productId);
    verify(productStockRepository, times(0)).save(any());
    verify(saleRepository, times(0)).save(any());
  }

  @Test
  void testSellProductClientNotFound() {
    when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      saleService.sellProduct(clientId, Collections.singletonList(productSaleRequest));
    });

    assertEquals("Client not found", exception.getMessage());

    verify(clientRepository, times(1)).findById(clientId);
    verify(productRepository, times(0)).findById(any());
    verify(productStockRepository, times(0)).findByProductId(any());
    verify(saleRepository, times(0)).save(any());
  }

  @Test
  void testSellProductProductNotFound() {
    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      saleService.sellProduct(clientId, Collections.singletonList(productSaleRequest));
    });

    assertEquals("Product not found", exception.getMessage());

    verify(clientRepository, times(1)).findById(clientId);
    verify(productRepository, times(1)).findById(productId);
    verify(productStockRepository, times(0)).findByProductId(any());
    verify(saleRepository, times(0)).save(any());
  }

  @Test
  void testGetPastSales() {
    Sale sale = new Sale(client, Collections.emptyList());
    when(saleRepository.findByClientId(clientId)).thenReturn(Collections.singletonList(sale));

    List<Sale> pastSales = saleService.getPastSales(clientId);

    assertEquals(1, pastSales.size());
    assertEquals(clientId, pastSales.getFirst().getClient().getId());

    verify(saleRepository, times(1)).findByClientId(clientId);
  }

  @Test
  void testHasSales() {
    when(saleRepository.existsByClientId(clientId)).thenReturn(true);

    boolean hasSales = saleService.hasSales(clientId);

    assertTrue(hasSales);

    verify(saleRepository, times(1)).existsByClientId(clientId);
  }

  @Test
  void testGetTop5MostSoldProducts() {
    ProductSale productSale = new ProductSale();
    productSale.setProduct(product);
    productSale.setQuantity(10);

    when(productSaleRepository.findTop5()).thenReturn(Collections.singletonList(productSale));

    List<ProductSale> top5MostSoldProducts = saleService.getTop5MostSoldProducts();

    assertEquals(1, top5MostSoldProducts.size());
    assertEquals(product, top5MostSoldProducts.getFirst().getProduct());

    verify(productSaleRepository, times(1)).findTop5();
  }
}
