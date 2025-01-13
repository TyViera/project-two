package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.ProductEntity;
import com.travelport.projecttwo.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerTest {
    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ClientController clientController;

    @Autowired
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }
    @Test
    void getProductsTest() throws Exception {
        ProductEntity product1 = new ProductEntity("1", "Product A", "123", 100);
        ProductEntity product2 = new ProductEntity("2", "Product B", "456", 200);
        List<ProductEntity> products = Arrays.asList(product1, product2);

        when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product B"));
    }

    @Test
    void getProductByIdTest() throws Exception {
        ProductEntity product = new ProductEntity("1", "Product A", "123", 100);

        when(productService.getProductById("1")).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product A"));

        when(productService.getProductById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProductTest() throws Exception {
        ProductEntity product = new ProductEntity("1", "Product A", "321", 100);

        when(productService.createProduct(any(ProductEntity.class))).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Product A",
                                  "code": "123"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("123"));
    }

    @Test
    void updateProductTest() throws Exception {
        ProductEntity product = new ProductEntity("1", "Product A", "123", 100);
        ProductEntity updatedProduct = new ProductEntity("1", "Updated Product", "321", 150);

        when(productService.updateProduct(eq("1"), any(ProductEntity.class))).thenReturn(Optional.of(updatedProduct));


        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\",\"code\":\"150\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("150"));

        when(productService.updateProduct(eq("999"), any(ProductEntity.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NonExisting\",\"code\":\"162\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProductTest() throws Exception {
        doNothing().when(productService).deleteProduct("1");

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        doThrow(new NullPointerException()).when(productService).deleteProduct("999");

        mockMvc.perform(delete("/products/999"))
                .andExpect(status().isNotFound());

        doThrow(new IllegalArgumentException()).when(productService).deleteProduct("2");

        mockMvc.perform(delete("/products/2"))
                .andExpect(status().isUnprocessableEntity());
    }

}
