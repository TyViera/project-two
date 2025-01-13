package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.controllers.ProductController;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.services.ProductServiceImpl;
import com.travelport.projecttwo.services.SaleService;
import com.travelport.projecttwo.services.ProductStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ProductServiceImpl productService;

  @Mock
  private SaleService saleService;

  @Mock
  private ProductStockService productStockService;

  @InjectMocks
  private ProductController productController;

  private ObjectMapper objectMapper = new ObjectMapper();

  private String basicAuthHeader;

  @BeforeEach
  void setup() {
    String username = "admin";
    String password = "badia123";
    basicAuthHeader = "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

    mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
  }

  @Test
  void createProductTest() throws Exception {
    Product product = new Product();
    product.setId(UUID.randomUUID());
    product.setName("Throne of Glass Bundle");
    product.setCode("TOG11");

    ProductStock productStock = new ProductStock();
    productStock.setProduct(product);
    productStock.setQuantity(0);

    when(productService.create(any(Product.class))).thenReturn(product);
    when(productStockService.create(any(ProductStock.class))).thenReturn(productStock);

    mockMvc.perform(post("/products")
            .header("Authorization", basicAuthHeader)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Throne of Glass Bundle"))
        .andExpect(jsonPath("$.code").value("TOG11"));

    verify(productStockService, times(1)).create(any(ProductStock.class));
  }

  @Test
  void getAllProductsTest() throws Exception {
    when(productService.getAllProducts()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/products")
            .header("Authorization", basicAuthHeader)
            .accept("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));

    verify(productService, times(1)).getAllProducts();
  }

  @Test
  void getProductByIdTest() throws Exception {
    UUID productId = UUID.randomUUID();
    Product product = new Product();
    product.setId(productId);
    product.setName("Throne of Glass Bundle");
    product.setCode("TOG11");

    when(productService.read(productId)).thenReturn(product);

    mockMvc.perform(get("/products/{id}", productId)
            .header("Authorization", basicAuthHeader)
            .accept("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Throne of Glass Bundle"))
        .andExpect(jsonPath("$.code").value("TOG11"));
  }

  @Test
  void updateProductTest() throws Exception {
    UUID productId = UUID.randomUUID();
    Product product = new Product();
    product.setId(productId);
    product.setName("Throne of Glass Bundle");
    product.setCode("TOG11");

    Product updatedProduct = new Product();
    updatedProduct.setId(productId);
    updatedProduct.setName("A Court of Thorns and Roses Bundle");
    updatedProduct.setCode("ACOTAR9");

    when(productService.update(eq(productId), any(Product.class))).thenReturn(updatedProduct);

    mockMvc.perform(put("/products/{id}", productId)
            .header("Authorization", basicAuthHeader)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updatedProduct)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("A Court of Thorns and Roses Bundle"))
        .andExpect(jsonPath("$.code").value("ACOTAR9"));
  }

  @Test
  void deleteProductTest() throws Exception {
    UUID productId = UUID.randomUUID();

    when(saleService.hasSales(productId)).thenReturn(false);

    ProductStock productStock = new ProductStock();
    Product product = new Product();
    product.setId(productId);
    productStock.setProduct(product);
    productStock.setQuantity(0);

    when(productStockService.getStockByProductId(productId)).thenReturn(productStock);

    mockMvc.perform(delete("/products/{id}", productId)
            .header("Authorization", basicAuthHeader))
        .andExpect(status().isNoContent());

    verify(productService, times(1)).delete(productId);
  }

  @Test
  void deleteProductWithSalesTest() throws Exception {
    UUID productId = UUID.randomUUID();

    when(saleService.hasSales(productId)).thenReturn(true);

    mockMvc.perform(delete("/products/{id}", productId)
            .header("Authorization", basicAuthHeader))
        .andExpect(status().isUnprocessableEntity());

    verify(productService, never()).delete(productId);
  }

  @Test
  void deleteProductWithStockTest() throws Exception {
    UUID productId = UUID.randomUUID();

    ProductStock productStock = new ProductStock();
    productStock.setProduct(null);
    productStock.setQuantity(10);

    when(saleService.hasSales(productId)).thenReturn(false);
    when(productStockService.getStockByProductId(productId)).thenReturn(productStock);

    mockMvc.perform(delete("/products/{id}", productId)
            .header("Authorization", basicAuthHeader))
        .andExpect(status().isUnprocessableEntity());

    verify(productService, never()).delete(productId);
  }
}
