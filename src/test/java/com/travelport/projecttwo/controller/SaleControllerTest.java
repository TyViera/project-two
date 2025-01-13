package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.controllers.SaleController;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductSale;
import com.travelport.projecttwo.requests.ClientRequest;
import com.travelport.projecttwo.requests.SaleRequest;
import com.travelport.projecttwo.requests.ProductSaleRequest;
import com.travelport.projecttwo.services.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class SaleControllerTest {

  private MockMvc mockMvc;

  @Mock
  private SaleService saleService;

  @InjectMocks
  private SaleController saleController;

  private ObjectMapper objectMapper = new ObjectMapper();
  private String basicAuthHeader;

  @BeforeEach
  void setup() {
    String username = "admin";
    String password = "badia123";
    basicAuthHeader = "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(saleController).build();
  }

  @Test
  void sellProductSuccessTest() throws Exception {
    // Create a valid UUID for the client
    UUID clientId = UUID.randomUUID();

    // Create a valid SaleRequest with the clientId
    SaleRequest saleRequest = new SaleRequest();
    saleRequest.setClient(new ClientRequest(clientId));  // Ensure the correct clientId is set

    // Create a ProductSaleRequest with a valid ID and quantity
    ProductSaleRequest productSaleRequest1 = new ProductSaleRequest();
    productSaleRequest1.setId(UUID.randomUUID());
    productSaleRequest1.setQuantity(10);

    // Add the product sale request to the sale request
    saleRequest.setProducts(Arrays.asList(productSaleRequest1));

    // Mock the service call (simulate the behavior without actual service logic)
    doNothing().when(saleService).sellProduct(eq(clientId), anyList());  // Ensure clientId is passed correctly

    // Perform the mock HTTP POST request with the SaleRequest as the JSON body
    mockMvc.perform(post("/sales")
            .header("Authorization", basicAuthHeader)  // Ensure the authorization header is passed
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saleRequest)))  // Serialize SaleRequest to JSON
        .andExpect(status().isCreated());  // Expect HTTP 201 Created status

    // Verify that the sellProduct method was called once with the correct arguments
    verify(saleService, times(1)).sellProduct(eq(clientId), anyList());  // Check the correct clientId is passed
  }




  @Test
  void sellProductFailureTest() throws Exception {
    SaleRequest saleRequest = new SaleRequest();
    saleRequest.setClient(new ClientRequest(UUID.randomUUID()));

    ProductSaleRequest productSaleRequest1 = new ProductSaleRequest();
    productSaleRequest1.setId(UUID.randomUUID());
    productSaleRequest1.setQuantity(10);

    saleRequest.setProducts(Arrays.asList(productSaleRequest1));

    doThrow(new RuntimeException("Bad Request")).when(saleService).sellProduct(any(UUID.class), anyList());

    mockMvc.perform(post("/sales")
            .header("Authorization", basicAuthHeader)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saleRequest)))
        .andExpect(status().isBadRequest());

    verify(saleService, times(1)).sellProduct(any(UUID.class), anyList());
  }

  @Test
  void getTop5MostSoldProductsSuccessTest() throws Exception {
    Product product1 = new Product("Throne of Glass Bundle", "TOG11");
    product1.setId(UUID.randomUUID());

    Product product2 = new Product("A Court of Thorns and Roses Bundle", "ACOTAR9");
    product2.setId(UUID.randomUUID());

    ProductSale productSale1 = new ProductSale(product1, null, 100);
    ProductSale productSale2 = new ProductSale(product2, null, 80);

    List<ProductSale> top5Sales = Arrays.asList(productSale1, productSale2);

    when(saleService.getTop5MostSoldProducts()).thenReturn(top5Sales);

    mockMvc.perform(get("/sales/most-sold-products")
            .header("Authorization", basicAuthHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].product.id").value(product1.getId().toString()))
        .andExpect(jsonPath("$[0].product.name").value("Throne of Glass Bundle"))
        .andExpect(jsonPath("$[0].quantity").value(100))
        .andExpect(jsonPath("$[1].product.id").value(product2.getId().toString()))
        .andExpect(jsonPath("$[1].product.name").value("A Court of Thorns and Roses Bundle"))
        .andExpect(jsonPath("$[1].quantity").value(80));

    verify(saleService, times(1)).getTop5MostSoldProducts();
  }
}
