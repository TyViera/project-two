package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entity.Product;
import com.travelport.projecttwo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProductsTest() throws Exception {
        Product product = new Product();
        product.setName("Product1");
        product.setCode("P001");

        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].code").value("P001"));
    }

    @Test
    void createProductTest() throws Exception {
        Product product = new Product();
        product.setName("Product1");
        product.setCode("P001");

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType("application/json")
                        .content("{\"name\":\"Product1\",\"code\":\"P001\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getProductByIdTest() throws Exception {
        Product product = new Product();
        product.setName("Product1");
        product.setCode("P001");

        when(productService.getProductById("1")).thenReturn(java.util.Optional.of(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("P001"));
    }

    @Test
    void updateProductTest() throws Exception {
        Product product = new Product();
        product.setName("Updated Product");
        product.setCode("P001");

        when(productService.updateProduct("1", any(Product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType("application/json")
                        .content("{\"name\":\"Updated Product\",\"code\":\"P001\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteProductTest() throws Exception {
        doNothing().when(productService).deleteProduct("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
